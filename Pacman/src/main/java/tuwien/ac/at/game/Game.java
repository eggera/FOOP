package main.java.tuwien.ac.at.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import main.java.tuwien.ac.at.client.ClientThread;


public abstract class Game {
		
	protected ClientThread clientThread;

	protected Player[] players;
	
	protected short field[][];

	protected int field_w; //# of horizontal cells
	protected int field_h; 
	
	
	public void movePlayers(int directions[]) 
	{		
		       //Right,Up,Left,Down
		int dirx[] = new int[]{1, 0,-1,0};
		int diry[] = new int[]{0,-1, 0,1};		
		short bad_here[]  = new short[]{Constants.RIGHT, Constants.TOP, Constants.LEFT, Constants.BOTTOM};
		short bad_there[] = new short[]{Constants.LEFT, Constants.BOTTOM, Constants.RIGHT, Constants.TOP};
	
		for(int i=0;i<players.length;i++)
		{
			int dir = directions[i];
			int x = players[i].getPosX();
			int y = players[i].getPosY();
			int nx = x + dirx[dir]; //stands for new/next_x
			int ny = y + diry[dir];
							
			if(nx < 0 || nx >= field_w || ny < 0 || ny >= field_h)
				continue;
			//The field here has a blocking wall
			if((field[x][y] & bad_here[dir])!=0)
				continue;
			//The field I want to go to has a blocking wall
			if((field[nx][ny] & bad_there[dir])!=0)
				continue;

			for(int j=0;j<players.length;j++)
				//if i can eat j and old or new position matches then eat
				if(players[i].getColor() == (players[j].getColor()+1)%players.length &&
						(( x  ==  players[j].getPosX() &&  y ==  players[j].getPosY()) ||
					     (nx  ==  players[j].getPosX() && ny ==  players[j].getPosY())))
				{
					players[i].addPoints(players[j].getPoints());
					players[j].setPoints(0);
					endGame();
				}
			
			players[i].setPosX((short)nx);
			players[i].setPosY((short)ny);
			
			if((field[nx][ny] & Constants.POINT)!=0)
			{
				players[i].addPoint();
				field[nx][ny] ^= Constants.POINT;
			}
		}
		
	}
	
	
	public void endGame()
	{
		//nach der angabe soll das nächste level beginnen wenn einer 
		//gefressen wurde, wir brauchen also noch irgendwo eine 2te punkteliste
	}
	
	
	
	public boolean canMove(int player, short direction)
	{
		return true;
	}
	
	public void sendKeyUp() {
		int[] dirs = new int[]{1,1,1};//test remove
		this.movePlayers(dirs);
		this.clientThread.sendKeyUp();
	}
	
	public void sendKeyDown() {
		int[] dirs = new int[]{3,3,3};//test remove
		this.movePlayers(dirs);
		this.clientThread.sendKeyDown();
	}
	
	public void sendKeyLeft() {
		int[] dirs = new int[]{2,2,2};//test remove
		this.movePlayers(dirs);
		this.clientThread.sendKeyLeft();
	}
	
	public void sendKeyRight() {
		int[] dirs = new int[]{0,0,0};//test remove
		this.movePlayers(dirs);
		this.clientThread.sendKeyRight();
	}
	
	
	public void draw(Graphics g){
		
		Graphics2D graphics = (Graphics2D) g;

		int width  = (int) graphics.getClipBounds().getWidth();
		int height = (int) graphics.getClipBounds().getHeight();

		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, width, height);	
		
		//"Constants"
		//Pointlist stuff
		int pointlist_border_t = 3; //outer border of layout list
		int font_s = Math.max(12,Math.min(width,height) / 16); //since I wanted the layout to "derive" from the fontsize the order got a bit ugly
		
		graphics.setFont( new Font("Arial", Font.PLAIN, font_s));
		int font_h = graphics.getFontMetrics().getHeight();
		int pointlist_w = graphics.getFontMetrics().stringWidth("00000") + font_h + 2 * pointlist_border_t;
		
		width -= pointlist_w;
		
		//Field Constants
		int wall_t = 2;  //wall thickness
		int cell_s = Math.min((width  - wall_t) / field_w, 
				              (height - wall_t) / field_h);
		int pacman_s = (cell_s - wall_t) *1/2;  
		int food_s =  pacman_s / 10;

		//everything is square so there is  a padding to the left or right
		//when the drawing area is not a square
		int pad_x = (width  - cell_s * field_w) / 2;
		int pad_y = (height - cell_s * field_h) / 2;
	
				
		//draw Walls
		graphics.setColor(Color.white);
		graphics.setStroke(new BasicStroke(wall_t));

		//i,j ~ y,x ~ h,w kinda wierd?
		for(int i=0; i< field_w;i++)
			for(int j=0;j<field_h;j++)
			{
				int x = pad_x + i * cell_s;
				int y = pad_y + j * cell_s;
				
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
			
		
		//draw Pacmans
		if(pacman_s>0)
			for(int i=0; i < players.length; i++)
				{
					int px = pad_x + players[i].getPosX() * cell_s + (cell_s - pacman_s)/2;
					int py = pad_y + players[i].getPosY() * cell_s + (cell_s - pacman_s)/2;
					
					Color color = Constants.COLORS[players[i].getColor()];				
					int rotation = 45 + players[i].getDirection() * 90; 

					drawPacman(graphics,px,py,pacman_s,color,rotation);
				}

		//Draw Pointlist
		int font_b = graphics.getFontMetrics().getAscent();
		int pointlist_h = font_h * players.length;

		int pointlist_x = pad_x + cell_s * field_w + pointlist_border_t;
		int pointlist_y = (height - pointlist_h)/2;
			
		for(int i=0; i< players.length; i++)
		{
			String points = String.valueOf(players[i].getPoints());
			int font_w = graphics.getFontMetrics().stringWidth(points);
			int line_y = pointlist_y + font_h  * i;
			
			graphics.setColor(Color.white);
			graphics.drawString(points, pointlist_x + pointlist_w - font_w - pointlist_border_t, line_y + font_b);
		
			Color color = Constants.COLORS[players[i].getColor()];				
			int rotation = 45 + players[i].getDirection() * 90; 
			
			drawPacman(graphics, pointlist_x + pointlist_border_t, line_y, font_h, color, rotation);
		}
		
	}
	
	//px,py as the topleft corner
	private void drawPacman(Graphics graphics,int px,int py, int size, Color color, int rotation)
	{            
		int mouth_angle = 90;
		
		graphics.setColor(color);
		graphics.fillArc(px, py, size,size, rotation, 360 - mouth_angle);
		
		double eye_rotation = (rotation+ mouth_angle/2 + 4) * Math.PI/180.0 ;
		int eye_size = size / 6  ;
		int eye_dist = size * 7/18;
		
		int eyex = px + size/2 - eye_size/2 + (int)(Math.cos(eye_rotation) * eye_dist);
		int eyey = py + size/2 - eye_size/2 - Math.abs( (int)(Math.sin(eye_rotation) * eye_dist) );
											//the abs is here so that the eye is always "up"
		graphics.setColor(Color.BLACK);
		graphics.fillOval(eyex, eyey, size/6, size/6);
	}
	
}
