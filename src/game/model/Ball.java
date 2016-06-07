package game.model;

import java.util.Random;

import game.gameMain;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball {
	
	Circle ball;
	Random rand =new Random();
	int p1 = 0 , p2 = 0;
	boolean movingRight = rand.nextBoolean();
	boolean movingDown = rand.nextBoolean();

	public Ball(double x,double y,double r,Paint value){
		ball = new Circle(x,y,r);
		ball.setFill(value);
	}
	
	public Shape getShape(){
		return ball;
	}
	
	//Overload
	
	public boolean isCollision(Ball ball, Paddle rect) {
		return ball.getShape().intersects(rect.getBounds());
	}
	
	public boolean isCollision(Ball ball, Wall wall) {
		return ball.getShape().intersects(wall.getBounds());
	}
	
	public void setCenterX(double d){
		ball.setCenterX(d);
	}
	public void setCenterY(double d){
		ball.setCenterY(d);
	}
	
	public void move(double Xspeed,double Yspeed) {
		if(Xspeed < 0 || Yspeed < 0) 
			return;  
		   
		if(isMovingRight()) 
			moveRight(Xspeed);
		else 
			moveLeft(Xspeed);

		if(isMovingDown())
			moveDown(Yspeed);
		else
			moveUp(Yspeed);
	}
	public void moveRight(double speed) {
	     movingRight = true;
	     ball.setCenterX(ball.getCenterX() + speed);
	}
	public void moveLeft(double speed) {
	   movingRight = false;
	     ball.setCenterX(ball.getCenterX() - speed);
	}
	public void moveUp(double speed) {
	     movingDown = false;
	     ball.setCenterY(ball.getCenterY() - speed);
	} 
	public void moveDown(double speed) {
	     movingDown = true;
	     ball.setCenterY(ball.getCenterY() + speed);
	}
	public boolean isMovingUp() {
	     return !movingDown;
	}
	public boolean isMovingDown() {
	     return movingDown;
	}
	public boolean isMovingRight() {
	     return movingRight;
	}
	public boolean isMovingLeft() {
	     return !movingRight;
	}
	
}