package main.java.tuwien.ac.at.game;



//You give it bare bones data and fills the blanks
public class ConstantLevel extends Level {
	private static final long serialVersionUID = -2474135363390494762L;

	
	public ConstantLevel(short field[][],short startx[],short starty[])
	{
		super.color_span = 10;
		super.color_time = 0;
		
		super.field = field;
		super.field_w = field.length;
		super.field_h = field[0].length; 

		super.players = new Player[startx.length];
		for(int i=0;i<startx.length;i++)
			players[i] = new Player(i,startx[i],starty[i]);
		
//		super.points = new int[startx.length];
//		for(int i=0;i<startx.length;i++)
//			points[i] = 0;
		
		for(int i=0; i<field_h;i++)
		{
			field[0][i] |= Constants.F_LEFT;
			field[field_w-1][i] |= Constants.F_RIGHT;
		}
		for(int i=0; i<field_w;i++)
		{
			field[i][0] |= Constants.F_TOP;
			field[i][field_h-1] |= Constants.F_BOTTOM;
		}

		players[1].setDirection(Constants.UP);
		players[2].setDirection(Constants.LEFT);
	}
	
	public ConstantLevel(ConstantLevel other, int maxplayers)
	{
		super.color_span = other.color_span;
		super.color_time = other.color_time;
		
		super.field = other.field;
		super.field_w = other.field.length;
		super.field_h = other.field[0].length; 
		
		maxplayers = Math.min(other.players.length, maxplayers);

		super.players = new Player[maxplayers];
		for(int i=0;i<maxplayers;i++)
			players[i] = new Player(i,other.players[i].getPosX(),other.players[i].getPosY());

	}
}
