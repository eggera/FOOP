package main.java.tuwien.ac.at.game;

public class Game1 extends Game{


	private static short field[][] = {{29, 17, 13},
						              {28, 16, 20},
						              {26, 19, 22}};
	
	private static short startx[] = {0,1,2};
	private static short starty[] = {0,0,0};
	
	public Game1(){
		
		super.field = field;
		super.field_w = 3;
		super.field_h = 3; 
		
		super.players = new Player[startx.length];
		for(int i=0;i<startx.length;i++)
			players[i] = new Player(i,startx[i],starty[i]);

	}
}
