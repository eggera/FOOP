package main.java.tuwien.ac.at.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

//I made Game a JPanel to save an extra class that only does "override paint"
public class Game extends JPanel {
	private static final long serialVersionUID = -5089217893590439611L;

	private String message = null;
	
	private int[] globalPoints;
	private int player_index;
	private Level level;
	private int  level_idx;
	
	public Game()	{	}

	public void startGame(int my_player_number,int players)
	{
		level_idx =0;
		player_index = my_player_number;
		globalPoints = new int[players];
		level = new ConstantLevel(Constants.LEVELS[level_idx],players);
	}
	
	public void movePlayers(int[] directions)
	{
		if(level==null) return;
		level.movePlayers(directions);
		if(level.isFinished())
			nextLevel();
	}
	
	public void nextLevel()
	{
		level.addPoints(globalPoints);
		if(++level_idx == Constants.LEVELS.length)
		{
			int best_index = 0; //find winner, if me? great!
			for(int i=0;i<globalPoints.length;i++)
				if(globalPoints[i]>globalPoints[best_index])
					best_index = i;
			if(best_index == player_index)
				message = "You won.";
			else
				message = "You lost.";
			level = null;
		}
		else
			level = new ConstantLevel(Constants.LEVELS[level_idx], level.getNrOfPlayers());
	}
	
	public void paint(Graphics g)
	{
		Graphics2D graphics = (Graphics2D)g;

		int width  = (int) graphics.getClipBounds().getWidth();
		int height = (int) graphics.getClipBounds().getHeight();
		
		if(message != null)
			drawMessageBox(graphics, message, 0, 0, width , height );
				
		
		if(level == null || message != null) return;

		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, width, height);	
		
		Player[] players = level.getPlayers();
		int field_w = level.getField_w();
		int field_h = level.getField_h();
		
		//"Constants"
		//about the Pointlist stuff
		/* this whole thing is a bit complicated:
		 *  i use the size of the whole drawable area to specify a font size for the pointlist
		 *  the pointlist reduces the width left for the actual level
		 *  then place the whole thing centered
		 * */
		int pointlist_border_t = 3; //outer border of layout list
		int font_s = Math.max(12,Math.min(width,height) / 16); 
		
		graphics.setFont( new Font("Arial", Font.PLAIN, font_s));
		int font_h = graphics.getFontMetrics().getHeight();
		int pointlist_w = graphics.getFontMetrics().stringWidth("00000") + font_h +  2 * pointlist_border_t;
		
		//Field Constants
		int wall_t = 2;  //wall thickness
		int cell_s = Math.min((width  - wall_t - pointlist_w) / field_w, 
				              (height - wall_t) / field_h);
		int pacman_s = (cell_s - wall_t) *1/2;  //actually the radius not the size
		int food_s =  pacman_s / 10;
		
		//everything is square so there is  a padding to the left or right
		//when the drawing area is not a square
		int pad_x = (width  - cell_s * field_w - pointlist_w) / 2 + 10;
		int pad_y = (height - cell_s * field_h) / 2;

		level.drawWalls  (graphics,pad_x,pad_y,cell_s,wall_t);
		level.drawPacmans(graphics,pad_x,pad_y,cell_s,pacman_s);
		level.drawFood   (graphics,pad_x,pad_y,cell_s,food_s);
		
		//Draw Pointlist

		int p1_s = font_h ;
		int p2_s = font_h * 2/3;
		int p3_s = font_h * 1/3;
		
		int pointlist_x = pad_x + cell_s * field_w + pointlist_border_t;
		int pointlist_y = (height - font_h * players.length)/2;
		
		
		int font_asc = graphics.getFontMetrics().getAscent();
		
		for(int i=0; i< players.length; i++)
		{
			String points = String.valueOf(players[i].getPoints());
			int font_w = graphics.getFontMetrics().stringWidth(points);
			int line_y = pointlist_y + font_h  * i;
			
			graphics.setColor(Color.white);
			graphics.drawString(points,pointlist_x + pointlist_w  - pointlist_border_t - font_w - 10, line_y + font_asc);
		
			Color color = Constants.COLORS[players[i].getColor()];
						
			level.drawPacman(graphics, pointlist_x + pointlist_border_t, line_y, font_h, color, 0, 90);
		}		

		int eatlist_x = pointlist_x;
		int eatlist_y = (height - font_h * players.length)/2 - p1_s;
		
		Color p1_c = Constants.COLORS[players[(player_index-1+players.length)%players.length].getColor()];
		Color p2_c = Constants.COLORS[players[player_index].getColor()];
		Color p3_c = Constants.COLORS[players[(player_index+1)%players.length].getColor()];
		
		level.drawPacman(graphics, eatlist_x + pointlist_border_t, eatlist_y, p1_s - 2, p1_c, 0, 90);
		level.drawPacman(graphics, eatlist_x + pointlist_border_t + p1_s * 2/3, eatlist_y + (p1_s - p2_s)/2, p2_s, p2_c, 0, 90);
		level.drawPacman(graphics, eatlist_x + pointlist_border_t + (p1_s + p2_s)* 2/3, eatlist_y + (p1_s - p3_s)/2, p3_s, p3_c, 0, 90);
		
		String eattime = Integer.toString(level.color_span - level.color_time);
		int font_w = graphics.getFontMetrics().stringWidth(eattime);
		graphics.setColor(Color.white);
		graphics.drawString(eattime,pointlist_x + pointlist_w  - pointlist_border_t - font_w - 10, eatlist_y + font_asc + (p1_s - font_h)/2);
		
	}

	// draw the info screen, before starting
	public void drawMessageBox(Graphics g, String msg, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.BLACK);
		g2d.fillRect(x, y, width, height);
		
		int padding = Math.min(width, height) / 12;
		
		x 		+= padding;
		y 		+= padding;
		width	-= padding * 2;
		height 	-= padding * 2;
		
		int center_x = x + width  / 2;
		int center_y = y + height / 2;
		
//		x = (int) g2d.getClipBounds().getCenterX() - x_bounds;
//		y = (int) g2d.getClipBounds().getCenterY() - y_bounds;
//		width  = x_bounds * 2;
//		height = y_bounds * 2;
		
		g2d.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawRect(x, y, width, height);
		
		g2d.setColor(new Color(50,30,200));
		Font font = new Font(Font.SERIF, Font.BOLD, Math.max(12, width/20));
		g2d.setFont(font);
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(msg, g2d);
		g2d.drawString(msg, center_x - (int)bounds.getWidth() /2
                          , center_y + (int)bounds.getHeight()/2);
	}
	
	public int[] getGlobalPoints() {
		return globalPoints;
	}

	
	public int getNrOfPlayers() {
		return level.getNrOfPlayers();
	}
	public void showMessageBox(String message) {
		this.message = message;
	}
	public boolean isRunning()
	{
		return level!=null && !level.isFinished();
	}
	
	
}
