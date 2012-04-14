package main.java.tuwien.ac.at.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;

import main.java.tuwien.ac.at.client.gui.Window;
import main.java.tuwien.ac.at.game.Constants;
import main.java.tuwien.ac.at.game.Level;

/**
 * This class contains the game's client logic
 */
public class ClientThread implements Runnable, KeyListener{

	Window window;
	
	private Socket socket;
	
	private PrintWriter out;
	private ObjectInputStream in;
	
	private Level game;
	
	public ClientThread(Window w,String serverName, int serverPort) 
						throws UnknownHostException, IOException {
		
		socket = new Socket(serverName, serverPort);
		out    = new PrintWriter(socket.getOutputStream(), true);
		in     = new ObjectInputStream(socket.getInputStream());
		
		window = w;

		w.addKeyListener(this);
	}
	
	public void run() {
		
		System.out.println("Press \"S\" to start");
		
		try {
		Object o ;
		while(socket.isConnected() && (o = in.readObject()) != null && !o.equals("")) {
				if(o instanceof String)
					processResponse((String) o);
				if(o instanceof Level)
					window.setLevel((Level) o); //TODO: global point stuff
			}			
		} catch (IOException e) {
			System.err.println("client: Disconnected, "+e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try	{	
				socket.close();
				}
			catch(Exception ign){}
			System.exit(0);
		}
	}
	
	public void setLevel(Level game) {
		this.game = game;
		window.setLevel(game);
	}
	
	public void processResponse(String response) {
		String[] directions = response.split(" ");
		int	  [] dirs = new int[game.getNrOfPlayers()];
		
		int i;
		for(i = 0; i < directions.length; ++i) {
			
			if(directions[i].equals("S"))
				dirs[i] = -1;
			else
				dirs[i] = Integer.parseInt(directions[i]);
		}
		// if there are less active players than visible
		for(; i < game.getNrOfPlayers(); ++i) {
			dirs[i] = -1;
		}
		
		game.movePlayers(dirs);
		window.repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		if(arg0.getKeyCode() == KeyEvent.VK_LEFT) 
			out.println(Constants.LEFT);
		
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) 
			out.println(Constants.RIGHT);
		
		if(arg0.getKeyCode() == KeyEvent.VK_UP) 
			out.println(Constants.UP);
		
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN) 
			out.println(Constants.DOWN);
		
		if(arg0.getKeyCode() == KeyEvent.VK_S)
			out.println("S");

		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
			out.println("");
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {	}

	@Override
	public void keyTyped(KeyEvent arg0) {	}
	
}
