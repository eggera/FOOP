package main.java.tuwien.ac.at.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Level implements Serializable {
	
	private static final long serialVersionUID = -5709916090882154480L;
	
	protected Player[] players;
	
	protected boolean end;

	protected short field[][];

	protected int field_w; //# of horizontal cells
	protected int field_h;
	
	protected int tick_duration;
	public int getTickDuration()
	{
		return tick_duration;
	}
	protected int color_span;
	protected int color_time;
		
	public void movePlayers(int directions[]) 
	{		
		color_time = (color_time + 1) % color_span;
		
		       //Right,Up,Left,Down
		int dirx[] = new int[]{1, 0,-1,0};
		int diry[] = new int[]{0,-1, 0,1};		
		short bad_here[]  = new short[]{Constants.F_RIGHT, Constants.F_TOP, Constants.F_LEFT, Constants.F_BOTTOM};
		short bad_there[] = new short[]{Constants.F_LEFT, Constants.F_BOTTOM, Constants.F_RIGHT, Constants.F_TOP};
	
		for(int i=0;i<players.length;i++)
		{
			if(color_time == 0)
				players[i].setColor((players[i].getColor() + 1)%players.length);
			
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
		
		boolean noFood = true; //there is no food left?, end the game
		for(int i=0; i< field_w && noFood;i++)
			for(int j=0;j<field_h;j++)
				if((field[i][j] & Constants.F_POINT) != 0) 
					noFood = false;
		end = end || noFood;
		
		for(int i=0; i < players.length && players.length > 0; i++) {
			
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

					end = true; //someone got eaten?, end the game
				}
			}
		}
		
	}
	
	// Determines whether this level is finished
	// Either a player gets eaten or all points were consumed
	public boolean isFinished() {
		return end;
	}
	
	// puts the points from all players into a point array
	public void addPoints(int[] globalPoints) {
		for(int i = 0; i < players.length; ++i)
			globalPoints[i] += players[i].getPoints();
	}
	
	public void drawWalls(Graphics2D graphics,int pad_x, int pad_y, int cell_s, int wall_t)
	{
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
	}

	public void drawPacmans(Graphics2D graphics,int pad_x, int pad_y, int cell_s,int pacman_s)
	{
		if(pacman_s>0) //to prevent crash from tiny window
			for(int i=0; i < players.length; i++)
				{
					int px = pad_x + players[i].getPosX() * cell_s + (cell_s - pacman_s)/2;
					int py = pad_y + players[i].getPosY() * cell_s + (cell_s - pacman_s)/2;
					
					Color color = Constants.COLORS[players[i].getColor()];				
					int rotation = players[i].getDirection() * 90; 

					//^^... smoothly animated mouths laaaaaag
					double mouth_angle = players[i].getMouthAngle();
					//mouth_angle = (players[i].getMouthAngle() + time / 14) % 180;
					//players[i].setMouthAngle(mouth_angle);
					//mouth_angle = Math.abs(90 - mouth_angle);
					
					if(mouth_angle == 10d) 
						mouth_angle = 10;
					else
						mouth_angle = 90;
					players[i].setMouthAngle(mouth_angle);	
					
					drawPacman(graphics,px,py,pacman_s,color,rotation, (int)mouth_angle);
				}
	}
	
	public void drawFood(Graphics2D graphics,int pad_x, int pad_y, int cell_s, int food_s)
	{
		graphics.setColor(Color.white);
		if(food_s>0) 	
			for(int i=0; i< field_w;i++)
				for(int j=0;j<field_h;j++)
					if((field[i][j] & Constants.F_POINT) != 0) 
						graphics.fillRect(pad_x + i * cell_s + (cell_s-food_s)/2,
								          pad_y + j * cell_s + (cell_s-food_s)/2, 
								          food_s, food_s);
	}

	/* the necessary time data for the (infeasible) smooth mouth animation
	private long lastTime = System.currentTimeMillis();
	public void draw(Graphics g){
		long currTime = System.currentTimeMillis();
		double time   = currTime - lastTime;
		lastTime      = currTime; */
	
	
	//px,py as the topleft corner
	public void drawPacman(Graphics2D graphics,int px,int py, int size, Color color, int rotation, int mouth_angle)
	{           
		if(size>0)
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
	}

	
	public int getNrOfPlayers() {
		return this.players.length;
	}
	
	public int getField_w() {
		return field_w;
	}

	public int getField_h() {
		return field_h;
	}

	public Player[] getPlayers() {
		return players;
	}
}
