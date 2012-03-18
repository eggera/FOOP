package main.java.tuwien.ac.at.gui;

import javax.swing.JFrame;

import main.java.tuwien.ac.at.gui.field.GameField;

@SuppressWarnings("serial")
public class Window extends JFrame {

	public Window(final GameField field){
		add(field);
		setTitle("Distributed PacMan");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        //setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
	}
}
