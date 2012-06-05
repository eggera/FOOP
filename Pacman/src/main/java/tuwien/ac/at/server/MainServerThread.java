package main.java.tuwien.ac.at.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainServerThread implements Runnable {

	private List<ClientHandler> clientList;
	
	private ServerTimer serverTimerThread;
	private ServerSocket serverSocket;
	
	//Accept new clients while running = false
	//once running=true the ServerTimer is running and any connection failure (or ESC) results in total shutdown
	private boolean running; 
	
	class ClientHandler implements Runnable {

		private Socket clientSocket;		
		private BufferedReader in;
		private ObjectOutputStream out;
		
		private int clientID()
		{
			return clientList.indexOf(this);
		}
		
		private boolean ready = false;
		
		private String lastAction = "0";
		
		public ClientHandler(Socket socket) throws IOException {
			this.clientSocket = socket;
			this.in  = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			this.out = new ObjectOutputStream(clientSocket.getOutputStream());
		}
			
		public void run() {
			
			// if the game is already running, the client connect is refused
			if(running) {
				
				try {
					send("running");
				} catch (IOException e) {
					System.err.println("Server: error when sending \"running\" to client "+clientID());
				} 
				try {
					while( (lastAction = in.readLine()) != null  &&  !lastAction.equals(""));
				} catch (IOException ex) {
					System.err.println("Server: Client " + clientID() + ": " + ex.getMessage());
				}
				close();
				return;
			}
				
			System.out.println("Server: Client " + clientID() + " waiting for start...");
			//wait till S or ..exit
			try	{
				while( (lastAction = in.readLine()) != null && !lastAction.equals("") && !lastAction.equals("S"));
			}catch(Exception ex) {
				System.err.println("Server: Client " + clientID() + ": " + ex.getMessage());
			}
			
			if(!"S".equals(lastAction)) {
				clientList.remove(this); //..Client did something stupid (or just pressed esc) but situation is recoverable
				close();
				return;
			}	

			System.out.println("Server: Client " + clientID() + " ready.");
			try {
				send("wait");
			} catch (IOException e1) {
				System.err.println("Server: Sending \"wait\" not possible for client "+clientID());
			}
			synchronized(clientList) {
				ready = true;
				boolean allReady = true; //in C# one could just write clientList.All((ch)=>ch.isReady) ;p 
				for(ClientHandler ch : clientList)
					if(!ch.isReady())
						allReady = false;	
			
				if(allReady && clientList.size() <= 3 && !running){
					System.out.println("Server: Client " + clientID() + " is beginning the game.");
					running = true;
					serverTimerThread = new ServerTimer();
					new Thread(serverTimerThread).start();
				}	
			}
			synchronized(this) {
				while(!running)
					try {
						wait();
					} catch (InterruptedException ign) { } //as long as we are not running we keep waiting
			}	

			System.out.println("Server: Client " + clientID() + " running...");
			//_THE_ basic reading loop most time will be spent in
			try {			
				while(running && (lastAction = in.readLine()) != null && !lastAction.equals(""));
				
			} catch (Exception e) {
				System.err.println("Server: Connection to Client "  + clientID() + " failed unexpectedly. \n"+e.getMessage());
			}
			finally{	
				System.out.println("Server: Client connection thread stopped.");
				endGame();
			}
		}

		public String getLastAction()
		{
			return lastAction;			
		}
		
		public boolean isReady() {
			return this.ready;
		}
		
		public void send(Object o) throws IOException
		{
			out.writeObject(o);
		}
		
		public void close()
		{
			if(!clientSocket.isClosed())
				try{
					clientSocket.close();
					out.writeObject("");
					System.out.println("Server: Client connection closed.");
				}catch(Exception ign){}
		}
	}
	
	public MainServerThread() {
		clientList = new ArrayList<ClientHandler>();
		clientList = Collections.synchronizedList(clientList);
	}
	
	public void endGame() {
		try{
			for(ClientHandler ch : clientList)
				ch.close();
			serverSocket.close();
		}catch(Exception ign){}
		System.exit(0);
	}
	
	public void run() {
		try {
			serverSocket = new ServerSocket(PacManServer.SERVER_PORT);
		} catch (IOException e) {
			System.err.println("Server: Failed to initialize socket.\n "+e.getMessage());
			return;
		}

		System.out.println("Server: Accepting new Clients...");
		while(!running  &&  clientList.size() < 3)
			try {
				ClientHandler toClient = new ClientHandler(serverSocket.accept());	
				if(!running)
					clientList.add(toClient);
				
				new Thread(toClient).start();
	
			} catch (IOException e) {
				//at this point one can still recover, just wait for more
				System.err.println("Server: Failed to establish client connection:\n"+e.getMessage());
				continue;
			}
		
		System.out.println("Server: Stopped accepting new Clients.");
	   //here-here
	}
	
	//I really really want to put this thread into here-here, but i have no good idea on how to prevent
	//the last serverSocker.accept waiting without disabling recoverability when the last player presses esc instead of start
	//..which could mean about 20 loc less^^
	class ServerTimer implements Runnable {

		public ServerTimer() {}
		
		public void run() {
			
			//wake all clients
			for(ClientHandler ch : clientList)
				synchronized(ch) {
					ch.notifyAll();
					try {
						ch.send("start " + clientList.indexOf(ch));
					} catch (IOException e) {
						System.err.println("Server: Error sending \"start\" to client "+ch.clientID());
					}
				}
				
			System.out.println("Server: Game running...");
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
				
				System.out.println("Server: Game ended.");
				
			} catch(Exception ex){
				System.err.println("Server: Game ended unexpectedly:\n" + ex.getMessage());
			}
			finally	{
				endGame();
			}
		}
	}
}
