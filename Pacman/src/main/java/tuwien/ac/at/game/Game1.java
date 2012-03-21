package main.java.tuwien.ac.at.game;

import main.java.tuwien.ac.at.client.ClientThread;

public class Game1 extends Game{
	

	private static short field[][] = {{1+8  ,       1,  1+4},
									  {8    ,1+4+8+16,    4},
								      {2+8  ,  2     ,  2+4}};
	
	private static short startx[] = {0,1,2};
	private static short starty[] = {0,0,0};
	
	public Game1(ClientThread clientThread){
		
		this.clientThread = clientThread;
		
		super.field = field;
		super.field_w = 3;
		super.field_h = 3; 
		
		super.players = new Player[startx.length];
		for(int i=0;i<startx.length;i++)
			players[i] = new Player(i,startx[i],starty[i]);

		players[1].setDirection(Player.UP);
		players[2].setDirection(Player.LEFT);
	}
}
