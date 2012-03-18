package main.java.tuwien.ac.at.gui.field;

import javax.swing.JPanel;

import main.java.tuwien.ac.at.player.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Dimension;

@SuppressWarnings("serial")
public abstract class GameField extends JPanel{
	//consts
	final private short padding = 24;
	
	@SuppressWarnings("unused")
	private final Player[] players = new Player[3];
	
	private Graphics2D graphics;
	
	private short height, width;
	
	protected short field[][];
	
	public GameField(final int width, final int height){
		this.width  = (short) (width  * padding);
		this.height = (short) (height * padding);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		graphics = (Graphics2D) g;
		Dimension dim = new Dimension(400, 400);
		
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, dim.width, dim.height);
		graphics.setStroke(new BasicStroke(2));
		
		drawBoard();
	}
	
	public void drawBoard() {
		
		graphics.setColor(Color.white);
		
		int i = 0, j = 0;
		
		for(int y = 0; y < height; y += padding){
			for(int x = 0; x < width; x += padding){
				if((field[i][j] & Constants.TOP) != 0){
					//draw top border
					graphics.drawLine(x, y, x+padding-1, y);
				}
				
				if((field[i][j] & Constants.BOTTOM) != 0){
					//draw bottom border
					graphics.drawLine(x, y+padding-1, x+padding-1, y+padding-1);
				}
				
				if((field[i][j] & Constants.RIGHT) != 0){
					//draw right border
					graphics.drawLine(x+padding-1, y, x+padding-1, y+padding-1);
				}
				
				if((field[i][j] & Constants.LEFT) != 0){
					//draw left border
					graphics.drawLine(x, y, x, y+padding-1);
				}
				
				if((field[i][j] & Constants.POINT) != 0){
					//draw point
					//graphics.setColor(Color.white);
					graphics.drawRect(x+11, y+11, 2, 2);
				}
				j++;
			}
			j=0;
			i++;
		}
	}
		
	
}
