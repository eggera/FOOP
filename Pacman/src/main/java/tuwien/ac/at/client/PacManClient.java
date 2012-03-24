package main.java.tuwien.ac.at.client;

import java.io.IOException;
import java.net.UnknownHostException;

import main.java.tuwien.ac.at.client.gui.Window;
import main.java.tuwien.ac.at.game.Game1;

import main.java.tuwien.ac.at.server.PacManServer;

/**
 * The client for the pacman game. It connects to the server and constantly sends
 * its game state and recieves status updates from the server.  
 */
public class PacManClient {

	private static ClientThread clientThread;
	
	/**
	 * @param serverIP, port
	 */
	public static void main(String[] args) {
		//connect to server
		try {
			clientThread = new ClientThread(
								PacManServer.SERVER_ADRESS, 
								PacManServer.SERVER_PORT
							);
			
			//load field
			Game1 f1 = new Game1(clientThread);
			
			//init gamefield
			new Window(f1);
			
			clientThread.setGame(f1);
			new Thread(clientThread).start();
			
		} catch (UnknownHostException e) {
			System.err.println("client: UnknownHostException, "+e.getMessage());
		} catch (IOException e) {
			System.err.println("client: IOException, "+e.getMessage());
		}		
	}
}
