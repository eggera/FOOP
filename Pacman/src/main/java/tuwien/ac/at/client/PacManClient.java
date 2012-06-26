package main.java.tuwien.ac.at.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import main.java.tuwien.ac.at.game.Game;

import main.java.tuwien.ac.at.server.PacManServer;

/**
 * The client for the pacman game. It connects to the server and constantly sends
 * its game state and recieves status updates from the server.  
 */
public class PacManClient {

	private static ClientThread clientThread;

	/**
	 * @param serverIP, port
	 */
	public static void main(String[] args) {
		
		String adress = "127.0.0.1";
		int port = 10000;
		//connect to server
		try
		{
			if(args.length > 0)
				adress = args[0];
			if(args.length > 1)
				port = Integer.parseInt(args[1]);		
		}
		catch(Exception e)
		{
			System.out.println("Parameter parsing failed using:");
		}
		System.out.println("Adress: " + adress);
		System.out.println("Port: " + port);
		
		
		JFrame w = new JFrame();

		Game game = new Game();
		w.add(game);
		
		w.setTitle("Distributed PacMan");
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(400, 400);
        w.setVisible(true);
        w.setResizable(true);
        
		try {
			
			clientThread = new ClientThread(game,	adress, port );

			w.addKeyListener(clientThread);
			
			new Thread(clientThread).start();
			
		} catch (UnknownHostException e) {
			game.showMessageBox("Error. Unknown host.");
			System.err.println("client: UnknownHostException, "+e.getMessage());
		} catch (IOException e) {
			game.showMessageBox("Error. IO Exception.");
			System.err.println("client: IOException, "+e.getMessage());
		}		
	}
}
