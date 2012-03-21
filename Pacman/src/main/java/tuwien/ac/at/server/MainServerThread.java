package main.java.tuwien.ac.at.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class MainServerThread implements Runnable {

	private List<ServerThread> clientList;
	
	private ServerSocket ss;
	
	
	public MainServerThread() {
		clientList = new ArrayList<ServerThread>();
	}
	
	public void run() {
		
		boolean listening = true;
		
		try {
			ss = new ServerSocket(PacManServer.SERVER_PORT);
		} catch (IOException e) {
			System.err.println("server: IO Exception, "+e.getMessage());
			return;
		}
		
		while(listening) {
			try {
				ServerThread toClient = new ServerThread(ss.accept());
				clientList.add(toClient);
				new Thread(toClient).start();
			} catch (IOException e) {
				System.err.println("server: IO Exception while listening, "+e.getMessage());
			}
		}
		
	}
	
}
