/**
 * PacServerThread references a tcp connection to exactly one client
 * 
 */

package server;

import java.net.*;
import java.io.*;

public class ServerInThread implements Runnable {

	Socket clientSocket;
	BufferedReader in;
	
	public ServerInThread(Socket socket) {
		this.clientSocket = socket;
	}
	
	public void run() {
		
		try {
			in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println("ServerInThread: IOException, "+e.getMessage());
			return;
		}
		
		while(true) {
			
			/*
			 * Listens for messages from client
			 */
			
		}
		
	}
	
}
