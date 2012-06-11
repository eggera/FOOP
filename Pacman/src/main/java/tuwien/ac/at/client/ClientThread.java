package main.java.tuwien.ac.at.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;


import main.java.tuwien.ac.at.game.Constants;
import main.java.tuwien.ac.at.game.Game;
/**
 * This class contains the game's client logic
 */
public class ClientThread implements Runnable, KeyListener{

	
	private Socket socket;
	
	private PrintWriter out;
	private ObjectInputStream in;
	
	private Game game;
	
	public ClientThread(Game g, String serverName, int serverPort) 
						throws UnknownHostException, IOException {

		socket = new Socket(serverName, serverPort);
		out    = new PrintWriter(socket.getOutputStream(), true);
		in     = new ObjectInputStream(socket.getInputStream());

		game   = g;
	}
	
	
	
	public void run() {
		
		System.out.println("Press \"S\" to start");
		game.showMessageBox("Press S to start.");
		
		try {
		Object o ;
		while(socket.isConnected() && (o = in.readObject()) != null && !o.equals(""))
			{			
				if(o instanceof String)
					processResponse((String) o);
				
			}			
		} catch (IOException e) {
			System.err.println("client: Disconnected, " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//catch(Exception ign){ign.printStackTrace();}
		finally {
			try	{	
				socket.close();
				}
			catch(Exception ign){}
			System.exit(0);
		}
	}

	public void processResponse(String response) {
		System.out.println("response = "+response);

		game.repaint();
		if(response.startsWith("wait")) {
			game.showMessageBox("Waiting for other players.");
			return;
		}
		
		if(response.startsWith("start")) { 
			//start playerIndex #ofPlayers 
			//I added this to allow a better "who eats who display"
			//and for an end message like "You won".	
			//the #ofPlayers are there to remove extra players
			
			String[] pars = response.split(" ");
			game.startGame(Integer.parseInt(pars[1]),Integer.parseInt(pars[2])); 
			game.showMessageBox(null);
			return;
		}
		
		if(response.startsWith("running")) {
			game.showMessageBox("Game is already running, sorry");
			return;
		}
		
		if(game.isRunning())
		{
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
		}
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
	public void keyReleased(KeyEvent arg0){   }

	@Override
	public void keyTyped(KeyEvent arg0)   {   }
	
}
