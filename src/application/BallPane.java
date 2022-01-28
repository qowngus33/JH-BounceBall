package application;

import java.util.Arrays;
import java.util.Vector;

import javafx.animation.*;
import javafx.application.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import javafx.util.Duration;

public class BallPane extends Pane {
	public final double radius = 10;
	private int x = (int) (PANE_WIDTH / 2 + radius), y = 120;
	private int dx = 1, dy = 1;
	private int score = 0;
	private int block_num = 0;
	private Circle circle = new Circle(x, y, radius);
	private Text startMessage = new Text(150, 20, "Click Anywhere To Start!!");
	private Rectangle Bar = new Rectangle(200, 400, 60, 25);
	
	private Rectangle R1 = new Rectangle(0, 30, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
	private Rectangle R2 = new Rectangle(150, 30, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
	private Rectangle R3 = new Rectangle(300, 30, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
	private Rectangle R4 = new Rectangle(0, 70, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
	private Rectangle R5 = new Rectangle(150, 70, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
	private Rectangle R6 = new Rectangle(300, 70, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);

	static int RECTANGLE_WIDTH = 148;
	static int RECTANGLE_HEIGHT = 38;
	static int PANE_WIDTH = 450;
	static int PANE_HEIGHT = 500;

	private Timeline animation;

	public BallPane() {
		circle.setFill(Color.DIMGREY);
		Bar.setFill(Color.CADETBLUE);
		R1.setFill(Color.DARKSEAGREEN);
		R2.setFill(Color.CADETBLUE);
		R3.setFill(Color.DARKCYAN);
		R6.setFill(Color.DARKSEAGREEN);
		R4.setFill(Color.CADETBLUE);
		R5.setFill(Color.DARKCYAN);

		
		getChildren().addAll(circle, Bar,startMessage,R1, R2, R3, R4, R5, R6);

		animation = new Timeline(new KeyFrame(Duration.millis(3), e -> moveBall()));
		animation.setCycleCount(Timeline.INDEFINITE);

		Bar.setOnMouseDragged(e -> {
			if (e.getX() > 0 && e.getX() < 450)
				Bar.setX(e.getX() - 7);

		});
	}

	public void play() {
		startMessage.setX(180);
		startMessage.setText("Score: " + this.score);
		animation.play();
	}

	public boolean checkLeft(int x, int y) {
		if (this.x >= x + radius && this.x <= x + radius + 3) {
			if (this.y <= y + RECTANGLE_HEIGHT + radius && this.y >= y - radius) {
				return true;
			}
		}
		return false;
	}

	public boolean checkRight(int x, int y) {
		if (this.x <= x + radius + RECTANGLE_WIDTH && this.x >= x + radius + RECTANGLE_WIDTH - 3) {
			if (this.y <= y + RECTANGLE_HEIGHT + radius && this.y >= y - radius) {
				return true;
			}
		}
		return false;
	}

	public boolean checkUp(int x, int y) {
		if (this.x >= x - radius && this.x <= x + RECTANGLE_WIDTH + radius)
			if (this.y >= y && this.y <= y + 2) {
				return true;

			}
		return false;
	}

	public boolean checkDown(int x, int y) {
		if (this.x >= x - radius && this.x <= x + RECTANGLE_WIDTH + radius)
			if (this.y >= y + RECTANGLE_HEIGHT && this.y <= y + 2 + RECTANGLE_HEIGHT) {
				return true;

			}
		return false;
	}

	public void pause() {
		animation.pause();
	}

	public DoubleProperty rateProperty() {
		return animation.rateProperty();
	}

	public void moveBall() {
		if (x < radius || x > getWidth() - radius) {
			dx *= -1;
		}
		if (y < radius) {
			dy *= -1;
		}
		if (x >= Bar.getX() - radius && x <= Bar.getX() + Bar.getWidth() + radius)
			if (y >= 394 && y <= 395) {
				dy *= -1;
			}

		checkBlock();

		x += dx;
		y += dy;
		circle.setCenterX(x);
		circle.setCenterY(y);
		startMessage.setText("Score: " + this.score);

		if (y >= getHeight() - radius - 5) {
			startMessage.setX(180);
			startMessage.setText("You Are Dead : (");
			animation.pause();
		}
		
		if(block_num==6)
		{
			startMessage.setText("You Win !!");
			animation.pause();
		}
	}

	public void checkBlock()
	{
		//R1
		if((checkLeft(0,30)&&R1.isVisible())||(checkRight(0,30)&&R1.isVisible()))
		{
			dx *= -1; 
			R1.setVisible(false);
			score += 10;
			block_num++;
		}
		else if((checkUp(0,30)&&R1.isVisible())||(checkDown(0,30)&&R1.isVisible()))
		{
			dy *= -1; 
			R1.setVisible(false);
			score += 10;
			block_num++;
		}
		//R2
		if((checkLeft(150,30)&&R2.isVisible())||(checkRight(150,30)&&R2.isVisible())) {
			dx *= -1; 
			R2.setVisible(false);
			score += 10;
			block_num++;
		}
		else if((checkUp(150,30)&&R2.isVisible())||(checkDown(150,30)&&R2.isVisible())) {
			dy *= -1; 
			R2.setVisible(false);
			score += 10;
			block_num++;
		}
		//R3 
		if((checkLeft(300,30)&&R3.isVisible())||(checkRight(300,30)&&R3.isVisible())) {
			dx *= -1; 
			R3.setVisible(false);
			score += 20;
			block_num++;
		}
		else if((checkUp(300,30)&&R3.isVisible())||(checkDown( 300,30)&&R3.isVisible())) {
			dy *= -1; 
			R3.setVisible(false);
			score += 20;
			block_num++;
		}
		
		//R4
		if((checkLeft(0,70)&&R4.isVisible())||(checkRight(0,70)&&R4.isVisible())) {
			dx *= -1; 
			R4.setVisible(false);
			score += 20;
			block_num++;
		}
		else if((checkUp(0,70)&&R4.isVisible())||(checkDown(0,70)&&R4.isVisible())) {
			dy *= -1; 
			R4.setVisible(false);
			score += 20;
			block_num++;
		}
		//R5
		if((checkLeft(150,70)&&R5.isVisible())||(checkRight(150,70)&&R5.isVisible())) {
			dx *= -1; 
			R5.setVisible(false);
			score += 20;
			block_num++;
		}
		else if((checkUp(150,70)&&R5.isVisible())||(checkDown(150,70)&&R5.isVisible())) {
			dy *= -1; 
			R5.setVisible(false);
			score += 20;
			block_num++;
		}
		//R6
		if((checkLeft( 300,70)&&R6.isVisible())||(checkRight(300,70)&&R6.isVisible())) {
			dx *= -1; 
			R6.setVisible(false);
			score += 20;
			block_num++;
		
		}
		else if((checkUp( 300,70)&&R6.isVisible())||(checkDown(300,70)&&R6.isVisible())) {
			dy *= -1; 
			R6.setVisible(false);
			score += 20;
			block_num++;
		}
		
	}

}
