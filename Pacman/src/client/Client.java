/**
 * The client for the pacman game. It connects to the server and constantly sends
 * its game state and recieves status updates from the server.  
 */


package client;

import server.Server;

public class Client {

	public static void main(String[] args) {
		
		new Thread(new ClientThread(Server.SERVER_ADRESS, Server.SERVER_PORT)).start();
		
	}
}
