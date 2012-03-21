package main.java.tuwien.ac.at.client;

import java.io.*;
import java.net.*;

/**
 * This class contains the game's client logic
 */
public class ClientThread implements Runnable {

	private Socket socket;
	
	private PrintWriter out;
	@SuppressWarnings("unused")
	private BufferedReader in;
	
	public ClientThread(String serverName, int serverPort) 
						throws UnknownHostException, IOException {

		socket = new Socket(serverName, serverPort);
		out    = new PrintWriter(socket.getOutputStream(), true);
		in     = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void run() {
		
		
		while(true) {
			
			try {
				String response = in.readLine();
				System.out.println("Response from server: "+response);
			} catch (IOException e) {
				System.err.println("client: IOException, "+e.getMessage());
			}
			
		}
		
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
