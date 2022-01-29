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
	private int x = (int) (PANE_WIDTH / 2 + radius), y = 205;
	private int dx = 1, dy = 1;
	private int score = 0;
	private int block_num = 0;
	private Circle circle = new Circle(x, y, radius);
	private Text startMessage = new Text(150, 20, "Click Anywhere To Start!!");
	private Rectangle Bar = new Rectangle(300, 500, 60, 25);

	public Rectangle R[][] = new Rectangle[6][4];

	static int RECTANGLE_WIDTH = 74;
	static int RECTANGLE_HEIGHT = 38;
	static int PANE_WIDTH = 450;
	static int PANE_HEIGHT = 500;

	private Timeline animation;

	public BallPane() {
		circle.setFill(Color.DIMGREY);
		Bar.setFill(Color.CADETBLUE);

		getChildren().addAll(circle, Bar, startMessage);

		SetBlocks();

		animation = new Timeline(new KeyFrame(Duration.millis(2), e -> moveBall()));
		animation.setCycleCount(Timeline.INDEFINITE);

		Bar.setOnMouseDragged(e -> {
			if (e.getX() > 0 && e.getX() < 450)
				Bar.setX(e.getX() - 7);

		});
	}

	public void SetBlocks() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				R[i][j] = new Rectangle(75 * i , 30 + 40 * j, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
				R[i][j].setFill(Color.DARKCYAN);
				getChildren().add(R[i][j]);
			}
		}
	}

	public boolean checkLeftOrRight(double x, double y) {
		boolean ans = false;
		if (this.y <= y + RECTANGLE_HEIGHT + radius + 1 && this.y >= y-1) {
			if ((this.x >= x - radius - 1 && this.x <= x - radius + 1)
					|| (this.x <= x + radius + RECTANGLE_WIDTH + 1 && this.x >= x + radius + RECTANGLE_WIDTH - 1)) {
				ans = true;
			} else {
				ans = false;
			}
		}
		return ans;
	}

	public boolean checkUpOrDown(double x, double y) {
		boolean ans = false;
		if (this.x >= x -1  && this.x <= x + RECTANGLE_WIDTH + 1) {
			if ((this.y >= y - radius - 1 && this.y <= y - radius + 1)
					|| (this.y <= y + RECTANGLE_HEIGHT + radius + 1 && this.y >= y + RECTANGLE_HEIGHT + radius - 1)) {
				ans = true;
			} else {
				ans = false;
			}
		}
		return ans;
	}
	
	public void play() {
		startMessage.setX(180);
		startMessage.setText("Score: " + this.score);
		animation.play();
	}

	public void pause() {
		animation.pause();
	}

	public DoubleProperty rateProperty() {
		return animation.rateProperty();
	}

	public void moveBall() {
		if (x < radius || x > getWidth() - radius) dx *= -1;
		if (y < radius) dy *= -1;
		if (x >= Bar.getX() - radius && x <= Bar.getX() + Bar.getWidth() + radius) {
			if (y >= 494 && y <= 495) {
				dy *= -1;
			} 
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
		if (block_num == 24) {
			startMessage.setText("You Win !!");
			animation.pause();
		}
	}

	public void checkBlock() {
		for(int i=0;i<6;i++) {
			for(int j=0;j<4;j++) {	
				if(R[i][j].isVisible()==false) continue;
				if (checkLeftOrRight(R[i][j].getX(), R[i][j].getY())) {
					dx *= -1;
					R[i][j].setVisible(false);
					score += 10;
					block_num++;
				}
				if (checkUpOrDown(R[i][j].getX(), R[i][j].getY())) {
					dy *= -1;
					R[i][j].setVisible(false);
					score += 10;
					block_num++;
				} 
				
			}
		}
	}

}
