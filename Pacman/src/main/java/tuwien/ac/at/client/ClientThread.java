package main.java.tuwien.ac.at.client;

import java.io.*;
import java.net.*;

import main.java.tuwien.ac.at.game.Game;
import main.java.tuwien.ac.at.game.Player;

/**
 * This class contains the game's client logic
 */
public class ClientThread implements Runnable {

	private Socket socket;
	
	private PrintWriter out;
	private BufferedReader in;
	
	private boolean active;
	private Game game;
	
	public ClientThread(String serverName, int serverPort) 
						throws UnknownHostException, IOException {

		socket = new Socket(serverName, serverPort);
		out    = new PrintWriter(socket.getOutputStream(), true);
		in     = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void run() {
		
		System.out.println("Press \"S\" to start");
		
		String response = "0 0 0 0"; // directions in case of 4 players
		while(socket.isConnected() && response != null && !response.equals("")) {
			try {
				response = in.readLine();
				System.out.println("Response from server: "+response);
				
				if(response != null  &&  !response.equals("")) {
					if(!active)
						active = true;
					
					processResponse(response);
				}
				
			} catch (IOException e) {
				System.err.println("client: Disconnected, "+e.getMessage());
			}
			
		}
		
		if(response == null  ||  response.equals("")) {
			if(!active)
				System.out.println("Game is running, wait for next game!");
			
			System.exit(1);
		}
		
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void sendStart() {
		out.println("S");
	}
	
	public void sendKeyUp() {
		out.println(Player.UP);
	}
	
	public void sendKeyDown() {
		out.println(Player.DOWN);
	}
	
	public void sendKeyLeft() {
		out.println(Player.LEFT);
	}
	
	public void sendKeyRight() {
		out.println(Player.RIGHT);
	}
	
	public void processResponse(String response) {
		String[] directions = response.split(" ");
		int	  [] dirs = new int[game.getNrOfPlayers()];
		
		int i;
		for(i = 0; i < directions.length; ++i) {
			if(directions[i].equals("S"))
				dirs[i] = -1;
			else
				dirs[i] = Integer.parseInt(directions[i]);
		}
		// if there are less active players than visible
		for(; i < game.getNrOfPlayers(); ++i) {
			dirs[i] = -1;
		}
		
		game.movePlayers(dirs);
	}
	
}
