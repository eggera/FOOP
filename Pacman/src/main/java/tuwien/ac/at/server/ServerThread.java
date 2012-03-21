/**
 * PacServerThread references a tcp connection to exactly one client
 * 
 */

package main.java.tuwien.ac.at.server;

import java.net.*;
import java.io.*;

public class ServerThread implements Runnable {

	private Socket clientSocket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	public ServerThread(Socket socket) {
		this.clientSocket = socket;
	}
	
	public void run() {
		
		try {
			in  = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println("ServerThread: IOException, "+e.getMessage());
			return;
		}
		
		while(true) {
			
			try {
			//	System.out.println("server1");
				String clientInput = in.readLine();
			//	System.out.println("server2");
				out.println(clientInput);
			} catch (IOException e) {
				System.err.println("server: IOException, "+e.getMessage());
				return;
			}
			
		}
		
	}
	
}
