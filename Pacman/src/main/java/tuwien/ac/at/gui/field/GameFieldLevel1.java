package main.java.tuwien.ac.at.gui.field;

@SuppressWarnings("serial")
public class GameFieldLevel1 extends GameField {
	//Game field for level1	
	private short field[][] = {{29, 17, 13}
						     , {28, 16, 20}
						     , {26, 19, 22}};
	
	public GameFieldLevel1() {
		super(3, 3);
		super.field = field;
	}
}
