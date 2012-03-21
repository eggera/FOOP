
/**
 * The server waits for incoming client connections and dispatches server threads
 *
 */


package main.java.tuwien.ac.at.server;


public class PacManServer {

	public static final String SERVER_ADRESS = "127.0.0.1";
	public static final int SERVER_PORT = 10000;
	public static final int GAME_SPEED= 500; //every second
	
	
	public static void main(String[] args) {
		
		new Thread(new MainServerThread()).start();
	}

}
