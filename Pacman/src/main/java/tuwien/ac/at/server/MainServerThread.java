package main.java.tuwien.ac.at.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MainServerThread implements Runnable {

	private List<ClientHandler> clientList;
	
	private ServerThread serverThread;
	private ServerSocket serverSocket;
	
	private int currentClientID;
	
	//if one client connection dies everything dies.
	private boolean running; 
	//assumed if a clients sends "" that a user pressed exited and therefore everything dies.
	
	class ClientHandler implements Runnable {

		private Socket clientSocket;		
		private BufferedReader in;
		private PrintWriter out;
		
		private boolean ready = false;
		private boolean wait;
		private boolean connectionError; // error in connecting running client, game is aborted
		private boolean endGame;
		
		private String lastAction = "0";
		
		public ClientHandler(Socket socket) throws IOException {
			this.clientSocket = socket;
			this.in  = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
		}
			
		public void run() {
			try {
				
				if(running)
					throw new ActiveGameException("Client may not connect, game is already running");
				
				while(!ready && lastAction != null && !lastAction.equals("")) {
					lastAction = in.readLine();
					if(lastAction.equals("S")) {
						ready = true;
						
						synchronized(serverThread) {
							serverThread.notifyAll();
						}
					}
				}
				
				System.out.println("client ready ");
				
				// wait for all clients to connect
				synchronized(this) {
					wait = true;
					wait();
					wait = false;
				}
				
				while(running && lastAction != null && !lastAction.equals("")) {
					lastAction = in.readLine();
				}
				
				if(lastAction == null  ||  lastAction.equals(""))
					endGame = true;
				
			} catch (IOException e) {
				System.err.println("Server: Connection to client failed unexpectedly. \n"+e.getMessage());
				connectionError = true;
			} catch (ActiveGameException age) {
				System.err.println("Server: "+age.getMessage());
			} catch(Exception e){
				System.err.println("client disconnected");
				connectionError = true;
			}
			finally{
				if(connectionError || endGame) {
					running = false;
					close();
					System.out.println("Server: Client connection thread stopped.");
				}
			}
		}

		public String getLastAction()
		{
			return lastAction;			
		}
		
		public boolean isReady() {
			return this.ready;
		}
		
		public boolean isWaiting() {
			return this.wait;
		}
		
		public void send(String s)
		{
			out.println(s);
		}
		
		public synchronized void wakeUpClient() {
			this.notifyAll();
		}
		
		public void close()
		{
			if(!clientSocket.isClosed())
				try{
					clientSocket.close();
					out.println("");
					System.out.println("Server: Client connection closed.");
				}catch(Exception ign){}
		}
	}
	
	public MainServerThread() {
		clientList = new ArrayList<ClientHandler>();
	}
	
	public List<ClientHandler> getClientList() {
		return this.clientList;
	}
	
	public int getClientID() {
		return this.currentClientID++;
	}
	
	public void startGame() {
		this.running = true;
		for(ClientHandler client : clientList) {
			while(!client.isWaiting()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {}
			}
			client.wakeUpClient();
		}
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public void closeConnection() {
		try{
			serverSocket.close();
		}catch(Exception ign){}
	}
	
	public void run() {
		try {
			serverSocket = new ServerSocket(PacManServer.SERVER_PORT);
		} catch (IOException e) {
			System.err.println("Server: Failed to initialize socket.\n "+e.getMessage());
			return;
		}
		
		serverThread = new ServerThread(this);
		new Thread(serverThread).start();

	//	try{
			while(!running) {
				ClientHandler toClient;
				try {
					if(clientList.size() < 3) {
						toClient = new ClientHandler(serverSocket.accept());	
						clientList.add(toClient);
						new Thread(toClient).start();
					}
				} catch (IOException e) {
					//at this point one can still recover, just wait for more
					System.err.println("Server: Failed to establish client connection.\n"+e.getMessage());
					continue;
				}
			}
			//System.out.println("Server: Stopped accepting new Clients.");
	//	}
	/*	finally{
				closeConnection();
		}*/
		
	}
	
}
