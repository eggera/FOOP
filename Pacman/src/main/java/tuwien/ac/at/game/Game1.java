package main.java.tuwien.ac.at.game;

import main.java.tuwien.ac.at.client.ClientThread;

public class Game1 extends Game{
	

	private static short field[][] = {{0  ,  0     ,  0},
		  							  {0  ,1+4+8+16,  0},
			                          {0  ,2+4+8+16,  0},
			                          {0  ,  0     ,  0},
				                      {0  ,1+4+8+16,  0},
					                  {0  ,2+4+8+16,  0},
		  							  {0  ,   0    ,  0}};
	
	private static short startx[] = {0,3,6};
	private static short starty[] = {0,2,0};
	
	public Game1(ClientThread clientThread){
		
		this.clientThread = clientThread;
		this.clientThread.setGame(this);
		
		super.field = field;
		super.field_w = field.length;
		super.field_h = field[0].length; 
		
		super.players = new Player[startx.length];
		for(int i=0;i<startx.length;i++)
			players[i] = new Player(i,startx[i],starty[i]);
		
		for(int i=0; i<field_h;i++)
		{
			field[0][i] |= Constants.LEFT;
			field[field_w-1][i] |= Constants.RIGHT;
		}
		for(int i=0; i<field_w;i++)
		{
			field[i][0] |= Constants.TOP;
			field[i][field_h-1] |= Constants.BOTTOM;
		}

		players[1].setDirection(Player.UP);
		players[2].setDirection(Player.LEFT);
	}
}
