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
		
		Window w = new Window();
		
		try {
			
			clientThread = new ClientThread(w,
								PacManServer.SERVER_ADRESS, 
								PacManServer.SERVER_PORT
							);

			
			clientThread.setLevel(Constants.CONSTANT_LEVEL1);
			
			new Thread(clientThread).start();
			
		} catch (UnknownHostException e) {
			w.showMessageBox(Constants.ERRORMSG);
			System.err.println("client: UnknownHostException, "+e.getMessage());
		} catch (IOException e) {
			w.showMessageBox(Constants.ERRORMSG);
			System.err.println("client: IOException, "+e.getMessage());
		}		
	}
}
