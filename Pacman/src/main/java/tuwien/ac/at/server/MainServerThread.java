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
	
	private ServerSocket serverSocket;

	//if one client connection dies everything dies.
	private boolean running = true; 
	//assumed if a clients sends "" that a user pressed exited and therefore everything dies.
	
	class ClientHandler implements Runnable {

		private Socket clientSocket;		
		private BufferedReader in;
		private PrintWriter out;
		
		private String lastAction = "0";
		
		public ClientHandler(Socket socket) throws IOException {
			this.clientSocket = socket;
			this.in  = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
		}
			
		public void run() {		
			try {
				while(running && lastAction != null && !lastAction.equals("")) 
					lastAction = in.readLine();					
			} catch (IOException e) {
				System.err.println("Server: Connection to client failed unexpectedly. \n"+e.getMessage());
			}
			finally{
				running = false;
				close();
				System.out.println("Server: Client connection thread stopped.");
			}
		}

		public String getLastAction()
		{
			return lastAction;			
		}		
		
		public void send(String s)
		{
			out.println(s);
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
	
	public void run() {
		try {
			serverSocket = new ServerSocket(PacManServer.SERVER_PORT);
		} catch (IOException e) {
			System.err.println("Server: Failed to initialize socket.\n "+e.getMessage());
			return;
		}

		try{
			while(running && clientList.size() == 0) {
				ClientHandler toClient;
				try {
					toClient = new ClientHandler(serverSocket.accept());	
				} catch (IOException e) {
					//at this point one can still recover, just wait for more
					System.err.println("Server: Failed to establish client connection.\n"+e.getMessage());
					continue;
				}
				clientList.add(toClient);
				new Thread(toClient).start();
			}
			System.out.println("Server: Stopped accepting new Clients.");
		}
		finally{
				try{
					serverSocket.close();
				}catch(Exception ign){}
			}
		System.out.println("Server: Game running.");
		try{
			synchronized(this)
			{
				while(running)
				{
					String message = "";
					for(int i=0;i<clientList.size();i++)
						message += clientList.get(i).getLastAction() + " ";
	
					for(int i=0;i<clientList.size();i++)
						clientList.get(i).send(message);
	
					System.out.println("Server: Gamestep: " + message);
					
					this.wait(PacManServer.GAME_SPEED);
				}
			}
			for(int i=0;i<clientList.size();i++)
				try{
					clientList.get(i).send("");
				}catch(Exception ign){} //try to send every client an -ending-

			System.out.println("Server: Game ended.");
		} catch(InterruptedException ex){
			System.out.println("Server: Game ended unexpectedly." + ex.getMessage());
		}
		
	}
	
}
