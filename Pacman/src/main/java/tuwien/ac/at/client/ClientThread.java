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

	private Window window;
	
	private Socket socket;
	
	private PrintWriter out;
	private ObjectInputStream in;
	
	private static int[] globalPoints;
	
	private Level level;
	private boolean gameEnds;
	
	public ClientThread(Window w, String serverName, int serverPort) 
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
		while(socket.isConnected() && (o = in.readObject()) != null && !o.equals("") && !gameEnds)
			{			
				if(o instanceof String)
					processResponse((String) o);

				if(level.isFinished()) {
					levelEnd();
				}
				
			}			
		} catch (IOException e) {
			System.err.println("client: Disconnected, " + e.getMessage());
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
	
	public void levelEnd() {
		calculateGlobalPoints();
		
		if(level.equals(Constants.CONSTANT_LEVEL1))
			setLevel(Constants.CONSTANT_LEVEL2);
		else
			gameEnds = true;
	}
	
	public void setLevel(Level game) {
		this.level = game;
		window.setLevel(game);
	}
	
	public Window getWindow() {
		return this.window;
	}
	
	public static int[] getGlobalPoints() {
		return globalPoints;
	}
	
	private void calculateGlobalPoints() {
		int[] points = level.getPlayerPoints();
		
		if(globalPoints == null)
			globalPoints = new int[points.length];
		
		for(int i = 0; i < points.length; ++i)
			globalPoints[i] += points[i];
	}
	
	public void processResponse(String response) {
		System.out.println("response = "+response);
		
		if(response.equals("wait")) {
			window.showMessageBox(Constants.WAITMSG);
			window.repaint();
			return;
		}
		
		if(response.equals("start")) {
			window.hideMessageBox(Constants.ALL);
			window.repaint();
			return;
		}
		
		if(response.equals("running")) {
			window.showMessageBox(Constants.GAMERUNNING);
			window.repaint();
			return;
		}
		
		String[] directions = response.split(" ");
		int	  [] dirs = new int[level.getNrOfPlayers()];
		
		int i;
		for(i = 0; i < directions.length; ++i) {
			
			if(directions[i].equals("S"))
				dirs[i] = -1;
			else
				dirs[i] = Integer.parseInt(directions[i]);
		}
		// if there are less active players than visible
		for(; i < level.getNrOfPlayers(); ++i) {
			dirs[i] = -1;
		}
		
		level.movePlayers(dirs);
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
	public void keyReleased(KeyEvent arg0){   }

	@Override
	public void keyTyped(KeyEvent arg0)   {   }
	
}
