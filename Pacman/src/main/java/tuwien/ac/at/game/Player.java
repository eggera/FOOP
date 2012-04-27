package main.java.tuwien.ac.at.game;

import java.io.Serializable;


class Player implements Serializable {
	private static final long serialVersionUID = 8572230220195777714L;
	
	//def. 
	private int   direction; //0..3 , 0=RIGHT , 1=TOP,.. if change watch out for draw
	private int   color;
	private int   points;
	private short posX;
	private short posY;
	private double   mouth_angle;

	public Player(){
		color = 0;
	}
	
	public Player(final int color, final short posX, final short posY){
		this.color = color;
		this.posX  = posX;
		this.posY  = posY;
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
	
	public int getPoints() {
		return points;
	}
	
	public void addPoint() {
		this.points++;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setMouthAngle(double mouth_angle) {
		this.mouth_angle = mouth_angle;
	}

	public double getMouthAngle() {
		return mouth_angle;
	}
}
