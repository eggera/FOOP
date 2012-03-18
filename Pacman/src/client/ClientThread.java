/**
 * This class contains the game's client logic
 */

package client;


import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {

	private Socket socket;
	private String serverName;
	private int serverPort;
	
	private PrintWriter out;
	private BufferedReader in;
	
	public ClientThread(String serverName, int serverPort) {
		this.serverName = serverName;
		this.serverPort = serverPort;
	}
	
	public void run() {
		
		try {
			this.socket = new Socket(serverName, serverPort);
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("client: UnknownHostException, "+e.getMessage());
			return;
		} catch (IOException e) {
			System.err.println("client: IOException, "+e.getMessage());
			return;
		}
		
		while(true) {
			/*
			 * client loop
			 * communicate with server via in/out
			 */
		}
		
	}
	
}
