
/**
 * The server waits for incoming client connections and dispatches server threads
 *
 */


package main.java.tuwien.ac.at.server;

import java.io.IOException;
import java.net.*;


public class PacManServer {

	public static final String SERVER_ADRESS = "127.0.0.1";
	public static final int SERVER_PORT = 10000;
	
	public static void main(String[] args) {
		
		ServerSocket ss = null;
		boolean listening = true;
		
		try {
			ss = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			System.err.println("IO Exception on server: "+e.getMessage());
			System.exit(1);
		}
		
		new Thread(new ServerOutThread());
		
		while(listening) {
			try {
				new Thread(new ServerInThread(ss.accept()));
			} catch (IOException e) {
				System.err.println("IO Exception while listening: "+e.getMessage());
			}
		}
		
	}

}
