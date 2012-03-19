package main.java.tuwien.ac.at.client.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import main.java.tuwien.ac.at.game.Game;

//I did the shuffling since I wanted the Gamelogic/Level representation seperated from the gui

public class GamePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	Game game;
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		game.draw(g);
	}
	
	public GamePanel(Game game)
	{
		this.game = game;		
	}
	
	//event handling 
}
