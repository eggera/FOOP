package main.java.tuwien.ac.at.client.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import main.java.tuwien.ac.at.game.Game;
import main.java.tuwien.ac.at.game.Player;

//I did the shuffling since I wanted the Gamelogic/Level representation seperated from the gui

public class GamePanel extends JPanel implements KeyListener
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
		
	//	this.addKeyListener(this);
	//	this.requestFocusInWindow();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT)
			game.keyTest(Player.LEFT);
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT)
			game.keyTest(Player.RIGHT);
		if(arg0.getKeyCode() == KeyEvent.VK_UP)
			game.keyTest(Player.UP);
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN)
			game.keyTest(Player.DOWN);

		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);

		arg0.getComponent().repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	//event handling 
}
