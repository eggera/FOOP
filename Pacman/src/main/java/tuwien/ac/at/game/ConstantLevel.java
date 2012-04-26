package main.java.tuwien.ac.at.game;



//You give it bare bones data and fills the blanks
public class ConstantLevel extends Level {
	private static final long serialVersionUID = -2474135363390494762L;

	
	public ConstantLevel(short field[][],short startx[],short starty[])
	{
		super.field = field;
		super.field_w = field.length;
		super.field_h = field[0].length; 

		super.players = new Player[startx.length];
		for(int i=0;i<startx.length;i++)
			players[i] = new Player(i,startx[i],starty[i]);
		
		super.mouthOpen = new boolean[super.players.length];
		
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
	
	
}
