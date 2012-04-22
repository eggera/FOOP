package main.java.tuwien.ac.at.client.gui;


import java.awt.Graphics;


import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.tuwien.ac.at.game.Level;

class GamePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Level level;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(level==null)
		{
			g.drawString("No Level set.",getWidth()/2,getHeight()/2);		
		}
		else
		{
			level.draw(g);
			//this.repaint();
		}
	}
	
	public GamePanel()	{}
	
	public void setLevel(Level level)
	{
		this.level = level;
	}
}

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	GamePanel pan;
	
	public Window(){
		pan = new GamePanel();
		
		add(pan);
		setTitle("Distributed PacMan");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        //setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
	}

	public void setLevel(Level level)
	{
		pan.setLevel(level);
	}
}
