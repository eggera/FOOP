package main.java.tuwien.ac.at.game;

import java.awt.Color;


public class Constants {
	/**
	 * integer codes for the maze
	 */
	public static short TOP = 1;
	public static short BOTTOM = 2;
	public static short RIGHT = 4;
	public static short LEFT = 8;
	public static short POINT = 16;

	//not quite happy with these constants here
	public static short MAX_PLAYERS = 3;
	public static Color[] COLORS = 
		new Color[]{ Color.red, 
					 Color.blue,
					 Color.green,
					 Color.yellow,
					 Color.cyan };
}
