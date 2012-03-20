package main.java.tuwien.ac.at.game;


public class Player {
	
	//def. 
	private int   direction; //0..3 , 0=RIGHT , 1=TOP,.. if change watch out for draw
	private int   color;
	private int   points = 0;
	private short posX;
	private short posY;
	private boolean alive;
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Player(){
		color = 0;
		alive = false;
	}
	
	public Player(final int color, final short posX, final short posY){
		this.color = color;
		this.posX  = posX;
		this.posY  = posY;
		this.alive = true;
	}
	
	public int addPoint(){
		return points++;
	}
	
	public int getPoints(){
		return points;
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
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
