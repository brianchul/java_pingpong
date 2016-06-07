package game.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Wall {
	
	Rectangle wall;
	
	public Wall(double x, double y,double W,double H,Paint value){

		wall = new Rectangle(x,y,W,H);
		wall.setFill(value);
	}
	
	public Shape getShape(){
		return wall;
	}
	
	public Bounds getBounds(){
		return wall.getBoundsInLocal();
	}

}