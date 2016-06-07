package game.model;

import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Paddle {
	
	Rectangle rect;
	
	private boolean GoUp ,GoDown ,GoLeft, GoRight ,running;
	public boolean p1fire = false,p2fire= false;
	
	public Paddle(double x, double y,double W,double H,Paint value) {
		rect = new Rectangle(x,y,W,H);
		rect.setFill(value);
	}
	
	public void move(double x, double y) {
		rect.setX(x);
		rect.setY(y);
	}
	
	public void Pressed(KeyEvent e,KeyCode up,KeyCode down,KeyCode left,KeyCode right,KeyCode run){
		if(e.getCode() == up )
			GoUp = true ;
		if(e.getCode() == down)
			GoDown = true ;
		if(e.getCode() == left){
			GoLeft = true ;
			p2fire =true;
		}
		if(e.getCode() == right){
			GoRight = true ;
			p1fire = true;
		}
		if(e.getCode() == run){
			running = true;
		}
	}
	
	public void Released(KeyEvent e,KeyCode up,KeyCode down,KeyCode left,KeyCode right,KeyCode run){
		if(e.getCode() == up )
			GoUp = false ;
		if(e.getCode() == down)
			GoDown = false ;
		if(e.getCode() == left){
			GoLeft = false ;
			p2fire =false;
		}
		if(e.getCode() == right){
			GoRight = false ;
			p1fire =false;
		}
		if(e.getCode() == run){
			running = false;
		}
	}
	
	public Shape getShape(){
		return rect;
	}
	
	public String location(){
		return "rect (x:"+rect.getX()+",y:"+rect.getY()+")";
	}
	
	public double positionX(){
		return rect.getX();
	}
	
	public double positionY(){
		return rect.getY();
	}
	
	public void moveInX(double dx) {
		rect.setX(rect.getX() + dx);
	}

	public void moveInY(double dy) {
		rect.setY(rect.getY() + dy);
	}
	public void boundary(Paddle rect,double leftX,double rightX,double upY,double downY){
		
		int dx = 0, dy = 0;
		
		if (GoUp)
			dy = -5;
		if (GoDown)
			dy = 5;
		if (GoLeft)
			dx = -5;
		if (GoRight)
			dx = 5;
		if(running)
			dx *= 2;

		if( rect.positionX() + dx > rightX || rect.positionX() + dx < leftX)
			dx = 0;
		if( rect.positionY() + dy > downY || rect.positionY() + dy < upY)
			dy = 0;
		
		rect.moveInX(dx);
		rect.moveInY(dy);
	}
	
	public Bounds getBounds(){
		return rect.getBoundsInLocal();
	}
	public double getHeight(){
		return rect.getHeight();
	}
	public double getWidth(){
		return rect.getWidth();
	}
}
