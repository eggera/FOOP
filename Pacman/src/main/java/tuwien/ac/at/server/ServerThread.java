/**
 * PacServerThread references a tcp connection to exactly one client
 * 
 */

package main.java.tuwien.ac.at.server;

import java.net.*;
import java.util.List;
import java.io.*;

import main.java.tuwien.ac.at.server.MainServerThread.ClientHandler;

public class ServerThread implements Runnable {

	private MainServerThread mst;
	
	private int clientsStarted;
	
	private BufferedReader in;
	private PrintWriter out;
	
	public ServerThread(MainServerThread mst) {
		this.mst = mst;
	}
	
	public void run() {
		
		synchronized(this) {
			while(clientsStarted == 0  ||  clientsStarted < mst.getClientList().size()) {
				try {
					System.out.println("waiting for clients to start ... ");
					wait();
				} catch (InterruptedException e1) {
					System.out.println("Client started");
					System.err.println("serverThread: Interrupted, "+e1.getMessage());
				}
				clientsStarted ++;
			}
		}
		
		System.out.println("clients online");
		
	/*	
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
		*/
	}
	
}
