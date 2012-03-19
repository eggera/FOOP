package main.java.tuwien.ac.at.client.gui;


import javax.swing.JFrame;

import main.java.tuwien.ac.at.game.Game;


@SuppressWarnings("serial")
public class Window extends JFrame {

	public Window(final Game game){
		GamePanel pan = new GamePanel(game);
		
		this.addKeyListener(pan);
		add(new GamePanel(game));
		setTitle("Distributed PacMan");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        //setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
	}
}
