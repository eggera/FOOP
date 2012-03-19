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
	
	
	public void keyTest(int d)
	{
		players[0].setDirection(d);
		
	}

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
		for(int i=0; i< field_w;i++)
			for(int j=0;j<field_h;j++)
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
			for(int i=0; i< field_w;i++)
				for(int j=0;j<field_h;j++)
					if((field[i][j] & Constants.POINT) != 0)
						graphics.fillRect(pad_x + i * cell_s + (cell_s-food_s)/2,
								          pad_y + j * cell_s + (cell_s-food_s)/2, 
								          food_s, food_s);
			
		int mouth_angle = 90;
		
		//draw Pacmans
		if(pacman_s>0)
			for(int i=0; i < players.length; i++)
				if(players[i].isAlive())
				{
					int x = pad_x + players[i].getPosX() * cell_s;
					int y = pad_y + players[i].getPosY() * cell_s;

					int rotation = 45 + players[i].getDirection() * 90;             
					
					int px = x + (cell_s - pacman_s)/2;
					int py = y + (cell_s - pacman_s)/2;

					graphics.setColor(Constants.COLORS[players[i].getColor()]);
					graphics.fillArc(px, py, pacman_s,pacman_s, rotation, 360 - mouth_angle);
					

					double eye_rotation = (rotation+ mouth_angle/2 + 4) * Math.PI/180.0 ;
					int eye_size = pacman_s / 6  ;
					int eye_dist = pacman_s * 7/18;
					
					int eyex = x + cell_s/2 - eye_size/2 + (int)(Math.cos(eye_rotation) * eye_dist);
					int eyey = y + cell_s/2 - eye_size/2 - Math.abs( (int)(Math.sin(eye_rotation) * eye_dist) );
														//the abs is here so that the eye is always "up"
					graphics.setColor(Color.BLACK);
					graphics.fillOval(eyex, eyey, pacman_s/6, pacman_s/6);
				}
		
	}
}
