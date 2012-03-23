package main.java.tuwien.ac.at.client;

import java.io.*;
import java.net.*;

/**
 * This class contains the game's client logic
 */
public class ClientThread implements Runnable {

	private Socket socket;
	
	private PrintWriter out;
	private BufferedReader in;
	
	public ClientThread(String serverName, int serverPort) 
						throws UnknownHostException, IOException {

		socket = new Socket(serverName, serverPort);
		out    = new PrintWriter(socket.getOutputStream(), true);
		in     = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void run() {
		
		System.out.println("Press \"S\" to start");
		
		String response = "0 0 0 0"; // right up left down
		while(socket.isConnected() && response != null && !response.equals("")) {
			try {
				response = in.readLine();
				System.out.println("Response from server: "+response);
			} catch (IOException e) {
				System.err.println("client: Disconnected, "+e.getMessage());
			}
			
		}
		
	}
	
	public void sendStart() {
		out.println("S");
	}
	
	public void sendKeyUp() {
		out.println("UP");
	}
	
	public void sendKeyDown() {
		out.println("DOWN");
	}
	
	public void sendKeyLeft() {
		out.println("LEFT");
	}
	
	public void sendKeyRight() {
		out.println("RIGHT");
	}
}
