package main.java.tuwien.ac.at.client;

import java.io.*;
import java.net.*;

/**
 * This class contains the game's client logic
 */
public class ClientThread implements Runnable {

	private Socket socket;
	
	@SuppressWarnings("unused")
	private PrintWriter out;
	@SuppressWarnings("unused")
	private BufferedReader in;
	
	public ClientThread(String serverName, int serverPort) 
						throws UnknownHostException, IOException {

		this.socket = new Socket(serverName, serverPort);
		out = new PrintWriter(socket.getOutputStream());
		in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void run() {
		
		
		while(true) {
			/*
			 * client loop
			 * communicate with server via in/out
			 */
		}
		
	}
	
}
