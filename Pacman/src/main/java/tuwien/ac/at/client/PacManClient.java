package main.java.tuwien.ac.at.client;

import main.java.tuwien.ac.at.gui.Window;

import main.java.tuwien.ac.at.gui.field.GameField;
import main.java.tuwien.ac.at.gui.field.GameFieldLevel1;
import main.java.tuwien.ac.at.server.PacManServer;

/**
 * The client for the pacman game. It connects to the server and constantly sends
 * its game state and recieves status updates from the server.  
 */
public class PacManClient {

	/**
	 * @param serverIP, port
	 */
	public static void main(String[] args) {
		//connect to server
		new Thread(new ClientThread(PacManServer.SERVER_ADRESS, PacManServer.SERVER_PORT)).start();
			
		//load field
		GameField f1 = new GameFieldLevel1();
		
		//init gamefield
		new Window(f1);
	}
}
