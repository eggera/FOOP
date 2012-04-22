package main.java.tuwien.ac.at.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Level implements Serializable {
	
	private static final long serialVersionUID = -5709916090882154480L;

	protected transient LevelEndHandler onLevelEnd;
	
	protected Player[] players;
//	protected int[] points;
	
	protected boolean end;

	protected short field[][];

	protected int field_w; //# of horizontal cells
	protected int field_h; 
	
	protected int tick_duration;
	public int getTickDuration()
	{
		return tick_duration;
	}
	
	public void movePlayers(int directions[]) 
	{		
		       //Right,Up,Left,Down
		int dirx[] = new int[]{1, 0,-1,0};
		int diry[] = new int[]{0,-1, 0,1};		
		short bad_here[]  = new short[]{Constants.F_RIGHT, Constants.F_TOP, Constants.F_LEFT, Constants.F_BOTTOM};
		short bad_there[] = new short[]{Constants.F_LEFT, Constants.F_BOTTOM, Constants.F_RIGHT, Constants.F_TOP};
	
		for(int i=0;i<players.length;i++)
		{
			// no more active players
			if(directions[i] == -1)
				continue;
			
			int dir = directions[i];
			int x = players[i].getPosX();
			int y = players[i].getPosY();
			int nx = x; //stands for new/next_x
			int ny = y;
			
			if(dir != -1) {
				nx = x + dirx[dir];
				ny = y + diry[dir];
				players[i].setDirection(dir);
			}			
							
			if(nx < 0 || nx >= field_w || ny < 0 || ny >= field_h)
				continue;
			//The field here has a blocking wall
			if((field[x][y] & bad_here[dir])!=0)
				continue;
			//The field I want to go to has a blocking wall
			if((field[nx][ny] & bad_there[dir])!=0)
				continue;
			
			players[i].setPosX((short)nx);
			players[i].setPosY((short)ny);
			
			if((field[nx][ny] & Constants.F_POINT)!=0)
			{
				players[i].addPoint();
				field[nx][ny] ^= Constants.F_POINT;
			}
		}
		
		for(int i=0; i < players.length; i++) {
			
			int nx = players[i].getPosX();
			int ny = players[i].getPosY();
			for(int j=0;j<players.length;j++) {
				//if i can eat j and old or new position matches then eat
				if(players[j].getColor() == (players[i].getColor()+1)%players.length &&
						(/*( x  ==  players[j].getPosX() &&  y ==  players[j].getPosY()) ||*/
					     (nx  ==  players[j].getPosX() && ny ==  players[j].getPosY())))
				{
					players[i].addPoints(players[j].getPoints());
					players[j].setPoints(0);
					endGame();
				}
			}
		}
		
	}
	
	public void endGame()
	{
//		if(onLevelEnd != null)
//			onLevelEnd.gameEnd();
		end = true;
	}
	
	// Determines whether this level is finished
	// Either a player gets eaten or all points were consumed
	public boolean isFinished() {
		return end;
	}
	
	// puts the points from all players into a point array
	public int[] getPlayerPoints() {
		int[] points = new int[players.length];
		for(int i = 0; i < players.length; ++i)
			points[i] = players[i].getPoints();
		return points;
	}
	
	//eating animation controlling
//	private long lastTime = System.currentTimeMillis();
	
	public void draw(Graphics g){
		
		/*
		 * TODO mouth animation or delete plzzz
		 */
//		long currTime = System.currentTimeMillis();
//		double time   = currTime - lastTime;
//		lastTime      = currTime;
		
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
				
				if((field[i][j] & Constants.F_TOP   ) != 0)
					graphics.drawLine(x ,y ,xe,y );
				if((field[i][j] & Constants.F_BOTTOM) != 0)
					graphics.drawLine(x ,ye,xe,ye);
				if((field[i][j] & Constants.F_RIGHT ) != 0)
					graphics.drawLine(xe,y ,xe,ye);
				if((field[i][j] & Constants.F_LEFT  ) != 0)
					graphics.drawLine(x ,y ,x ,ye);
			}
	
		boolean foodExists = false;
		
		//draw Food
		graphics.setColor(Color.white);
		if(food_s>0) 	
			for(int i=0; i< field_w;i++)
				for(int j=0;j<field_h;j++)
					if((field[i][j] & Constants.F_POINT) != 0) {
						graphics.fillRect(pad_x + i * cell_s + (cell_s-food_s)/2,
								          pad_y + j * cell_s + (cell_s-food_s)/2, 
								          food_s, food_s);
						
						foodExists = true;
					}
		
		if(!foodExists) 
			endGame();
			
		
		//draw Pacmans
		if(pacman_s>0)
			for(int i=0; i < players.length; i++)
				{
					int px = pad_x + players[i].getPosX() * cell_s + (cell_s - pacman_s)/2;
					int py = pad_y + players[i].getPosY() * cell_s + (cell_s - pacman_s)/2;
					
					Color color = Constants.COLORS[players[i].getColor()];				
					int rotation = players[i].getDirection() * 90; 

					//^^... animated mouths laaaaaag
					double mouth_angle =  90;//(players[i].getMouthAngle() + time / 14) % 180;
					//players[i].setMouthAngle(mouth_angle);
					//mouth_angle = Math.abs(90 - mouth_angle);
										
					drawPacman(graphics,px,py,pacman_s,color,rotation, (int)mouth_angle);
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
			
			int rotation = Constants.RIGHT * 90;
			
			drawPacman(graphics, pointlist_x + pointlist_border_t, line_y, font_h, color, rotation, 90);
		}
		
	}
	
	//px,py as the topleft corner
	private void drawPacman(Graphics graphics,int px,int py, int size, Color color, int rotation, int mouth_angle)
	{            
		
		graphics.setColor(color);
		graphics.fillArc(px, py, size,size, rotation + mouth_angle/2, 360 - mouth_angle);
		
		double eye_rotation = (rotation+ 90 + 4) * Math.PI/180.0 ;
		int eye_size = size / 6  ;
		int eye_dist = size * 7/18;
		
		int eyex = px + size/2 - eye_size/2 + (int)(Math.cos(eye_rotation) * eye_dist);
		int eyey = py + size/2 - eye_size/2 - Math.abs( (int)(Math.sin(eye_rotation) * eye_dist) );
											//the abs is here so that the eye is always "up"
		graphics.setColor(Color.BLACK);
		graphics.fillOval(eyex, eyey, size/6, size/6);
	}

	
	public int getNrOfPlayers() {
		return this.players.length;
	}
	
}
