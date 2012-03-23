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
			
			System.out.println("waiting for clients to start ... ");
			while(clientsStarted == 0  ||  clientsStarted < mst.getClientList().size()) {
				try {
					wait();
				} catch (InterruptedException e1) {
					System.err.println("serverThread: Interrupted, "+e1.getMessage());
				}
				clientsStarted ++;
			}
		}

		mst.startGame();
		
		System.out.println("clients online");
		
		
		/*
		System.out.println("Server: Game running.");
		try{
			while(running)
			{
				String message = "";
				for(int i=0;i<clientList.size();i++)
					message += clientList.get(i).getLastAction() + " ";

				for(int i=0;i<clientList.size();i++)
					clientList.get(i).send(message);

				System.out.println("Server: Gamestep: " + message);
				
				Thread.sleep(PacManServer.GAME_SPEED);
			}
			for(int i=0;i<clientList.size();i++)
				try{
					clientList.get(i).send("");
				}catch(Exception ign){} //try to send every client an -ending-

			System.out.println("Server: Game ended.");
		} catch(InterruptedException ex){
			System.out.println("Server: Game ended unexpectedly." + ex.getMessage());
		} */
		
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
