
/**
 * The server waits for incoming client connections and dispatches server threads
 *
 */

package main.java.tuwien.ac.at.server;

import java.net.*;

public class PacManServer {

	public static  int SERVER_PORT		 = 10000;
	public static  int GAME_SPEED		 = 1000; //every second
	
	
	public static void main(String[] args) {
		
		try	{
			if(args.length > 0)
				SERVER_PORT = Integer.parseInt(args[0]);	
			if(args.length > 1)
				GAME_SPEED = Integer.parseInt(args[1]);	
			
		}
		catch(Exception e){
			System.out.println("Parameter parsing failed using:");
		}
		/*try	{
			System.out.println("Adress: " + InetAddress.getLocalHost().getHostAddress());	
		}catch(Exception e){}*/

		System.out.println("Port: " + SERVER_PORT);
		System.out.println("Gamespeed: " + GAME_SPEED);
		
		
		new Thread(new MainServerThread()).start();
	}
}
