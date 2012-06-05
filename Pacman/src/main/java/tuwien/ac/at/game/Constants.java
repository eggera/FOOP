package main.java.tuwien.ac.at.game;

import java.awt.Color;


public class Constants {
	/**
	 * integer codes for the maze
	 */
	public static short F_TOP = 1;
	public static short F_BOTTOM = 2;
	public static short F_RIGHT = 4;
	public static short F_LEFT = 8;
	public static short F_POINT = 16;

	//not quite happy with these constants here
	public static short MAX_PLAYERS = 3;
	public static Color[] COLORS = 
		new Color[]{ Color.red, 
					 Color.blue,
					 Color.green,
					 Color.yellow,
					 Color.cyan,
					 Color.orange,
					 Color.gray };

	public static final int RIGHT = 0;
	public static final int UP    = 1;
	public static final int LEFT  = 2;
	public static final int DOWN  = 3;

	private static short startx1[] = {0,3,6,1,3};
	private static short starty1[] = {0,2,0,1,3};
	private static short field1[][] =
		{{0  ,  0     ,  0},
		 {0  ,1+4+8+16,  0},
         {0  ,2+4+8+16,  0},
         {0  ,  0     ,  0},
         {0  ,1+4+8+16,  0},
         {0  ,2+4+8+16,  0},
		 {0  ,   0    ,  0}};



	private static short startx2[] = {0,2,4,1,3};
	private static short starty2[] = {0,0,2,1,3};
	private static short field2[][] =
		{{0  ,  0     ,  0},
		 {16 ,1+4+8+16, 16},
         {0  ,  0     , 16},
         {16 ,2+4+8+16, 16},
		 {0  ,   0    ,  0}};

	

	private static short startx3[] = {0,2,5,5,5,5};
	private static short starty3[] = {0,5,2,5,0,0};
	private static short field3[][] =
		{{0  ,  0     ,  0, 16, 16, 16},
		 {16 ,1+4+8+16, 16, 16, 16, 16},
         {0  ,  0     , 16, 16, 16, 16},
         {16 ,2+4+8+16, 16, 16, 16, 16},
		 {0  ,   0    ,  0, 8+16, 8+16, 8+16},
		 {0  ,   0    ,  0, 16, 16, 16}};

	public static ConstantLevel[] LEVELS = new ConstantLevel[]{
		 new ConstantLevel(field1,startx1,starty1),
		 new ConstantLevel(field2,startx2,starty2),
		 new ConstantLevel(field3,startx3,starty3)
	};
}
