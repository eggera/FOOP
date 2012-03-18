package main.java.tuwien.ac.at.player;

public class Player {
	//def. 
	private int   color;
	private int   points = 0;
	private short posX;
	private short posY;
	
	public Player(){
		color = 0;
	}
	
	public Player(final int color, final short posX, final short posY){
		this.color = color;
		this.posX  = posX;
		this.posY  = posY;
	}
	
	public int changeColor(){
		return color = (color + 1) % 3;
	}
	
	public int addPoint(){
		return points++;
	}
	
	public int transferPoints(){
		return points;
	}
	
	public short getPosX(){
		return posX;
	}
	
	public short getPosY(){
		return posY;
	}
	
	public void setPosX(final short posX){
		this.posX = posX;
	}
	
	public void setPosY(final short posY){
		this.posY = posY;
	}
}
