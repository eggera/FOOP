package main.java.tuwien.ac.at.client;

import java.io.IOException;
import java.net.UnknownHostException;

import main.java.tuwien.ac.at.client.gui.Window;
import main.java.tuwien.ac.at.game.Constants;

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
			Window w = new Window();
			
			clientThread = new ClientThread(w,
								PacManServer.SERVER_ADRESS, 
								PacManServer.SERVER_PORT
							);

			
			clientThread.setLevel(Constants.LEVEL2);
			
			new Thread(clientThread).start();
			
		} catch (UnknownHostException e) {
			System.err.println("client: UnknownHostException, "+e.getMessage());
		} catch (IOException e) {
			System.err.println("client: IOException, "+e.getMessage());
		}		
	}
}
