package main.java.tuwien.ac.at.game;



//this exists so that the game package now does not have any references to the "outside" world
public interface LevelEndHandler {

	void gameEnd(int[] points);//i kinda dislike this parameter^^

}
