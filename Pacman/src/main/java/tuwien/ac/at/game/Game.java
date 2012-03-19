package main.java.tuwien.ac.at.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public abstract class Game {

	protected Player[] players;
	
	protected short field[][];

	protected int field_w; //# of horizontal cells
	protected int field_h; 
	
	

	public void moveStuff() //get direction everybody moves in
	{		
		//foreach player move player
	}
	
	public boolean canMove(int player, short direction)
	{
		return true;
	}
	
	
	public void draw(Graphics g){
		
		Graphics2D graphics = (Graphics2D) g;

		int width  = (int) graphics.getClipBounds().getWidth();
		int height = (int) graphics.getClipBounds().getHeight();

		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, width, height);	
		
		//"Constants"
		
		int wall_t = 2;  //wall thickness
		int cell_s = Math.min((width  - wall_t) / field_w, 
				              (height - wall_t) / field_h);
		int pacman_s = (cell_s - wall_t) *1/2;  
		int food_s =  pacman_s / 10;

		//everything is square so there is  a padding to the left or right
		//when the drawing are is not square
		int pad_x = (width  - cell_s * field_w) / 2;
		int pad_y = (height - cell_s * field_h) / 2;
	
		
			
		//draw Walls
		graphics.setColor(Color.white);
		graphics.setStroke(new BasicStroke(wall_t));

		//i,j ~ y,x ~ h,w kinda wierd?
		for(int i=0; i< field_h;i++)
			for(int j=0;j<field_w;j++)
			{
				int x = pad_x + j * cell_s;
				int y = pad_y + i * cell_s;
				
				int xe = x + cell_s;
				int ye = y + cell_s;
				
				if((field[i][j] & Constants.TOP   ) != 0)
					graphics.drawLine(x ,y ,xe,y );
				if((field[i][j] & Constants.BOTTOM) != 0)
					graphics.drawLine(x ,ye,xe,ye);
				if((field[i][j] & Constants.RIGHT ) != 0)
					graphics.drawLine(xe,y ,xe,ye);
				if((field[i][j] & Constants.LEFT  ) != 0)
					graphics.drawLine(x ,y ,x ,ye);
			}
	
		//draw Food
		graphics.setColor(Color.white);
		if(food_s>0) 	
			for(int i=0; i< field_h;i++)
				for(int j=0;j<field_w;j++)
					if((field[i][j] & Constants.POINT) != 0)
						graphics.fillRect(pad_x + i * cell_s + (cell_s-food_s)/2,
								          pad_y + j * cell_s + (cell_s-food_s)/2, 
								          food_s, food_s);
			
		//draw Pacmans
		if(pacman_s>0)
			for(int i=0; i < players.length; i++)
				if(players[i].isAlive())
				{
					graphics.setColor(Constants.COLORS[players[i].getColor()]);
					
					int x = pad_x + players[i].getPosX() * cell_s + (cell_s - pacman_s)/2;
					int y = pad_y + players[i].getPosY() * cell_s + (cell_s - pacman_s)/2;
			
					graphics.fillOval(x, y, pacman_s,pacman_s);	
				}
		
	}
}
