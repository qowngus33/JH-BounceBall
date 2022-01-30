package application;

import java.util.Arrays;
import java.util.Vector;

import javafx.animation.*;
import javafx.application.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import javafx.util.Duration;

public class BallPane extends Pane 
{
	public final double radius = 10;
	private int x = (int) (PANE_WIDTH / 2 + radius), y = 235;
	private int dx = 1, dy = 1;
	private int score = 0;
	private int block_num = 0;
	private Circle circle = new Circle(x, y, radius);
	private Text startMessage = new Text(150, 20, "Click Anywhere To Start!!");
	private Text countMessage = new Text(215, 350, "3");
	private Rectangle Bar = new Rectangle(300, 500, BAR_WIDTH, 24);
	private Button Restart = new Button("RESTART");

	private Rectangle R[][] = new Rectangle[BLOCk_ROW][BLOCk_COLUMN];
	//이차원 배열에 블록들을 저장한다.

	static int RECTANGLE_WIDTH = 44;
	static int RECTANGLE_HEIGHT = 28;
	static int PANE_WIDTH = 450;
	static int PANE_HEIGHT = 500;
	static int BLOCk_ROW = 10;
	static int BLOCk_COLUMN = 6;
	static int BAR_WIDTH = 100;

	private Timeline animation;

	public BallPane() {
		circle.setFill(Color.WHITE);
		Bar.setFill(Color.WHITE);
		startMessage.setFill(Color.WHITE);
		countMessage.setVisible(false);
		countMessage.setStyle("-fx-font: 60 arial;");
		countMessage.setFill(Color.WHITE);

		getChildren().addAll(circle, Bar, startMessage,countMessage);
		String enteredByUser = "051821";
		setStyle("-fx-background-color: #" + enteredByUser);

		SetBlocks();
		getChildren().add(Restart);

		animation = new Timeline(new KeyFrame(Duration.millis(2), e -> moveBall()));
		animation.setCycleCount(Timeline.INDEFINITE);
		
		setOnMouseClicked(e->{
			play();
		});

		Bar.setOnMouseDragged(e -> {
			if (e.getX() > 0 && e.getX() < 450)
				Bar.setX(e.getX() - 7);
		});
		
		Restart.setOnMouseClicked(e -> {
			try {
				reStart();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
	}

	public void SetBlocks() {
		//블록들을 초기화 시키는 함수 
		for (int i = 0; i < BLOCk_ROW; i++) {
			for (int j = 0; j < BLOCk_COLUMN; j++) {
				R[i][j] = new Rectangle((RECTANGLE_WIDTH+1) * i, 30 + (RECTANGLE_HEIGHT+1) * j, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
				if(j%4==0) {
					R[i][j].setFill(Color.ALICEBLUE);
				} else if(j%4==1) {
					R[i][j].setFill(Color.LIGHTBLUE);
				} else if(j%4==2) {
					R[i][j].setFill(Color.CORNFLOWERBLUE);
				} else if(j%4==3) {
					R[i][j].setFill(Color.LIGHTBLUE);
				} else {				
					R[i][j].setFill(Color.CORNFLOWERBLUE);
				}
				getChildren().add(R[i][j]);
			}
		}
	}

	public boolean checkLeftOrRight(double x, double y) 
	{ //공이 블록의 왼쪽 또는 오른쪽에 부딫힌 경우 
		boolean ans = false;
		if (this.y <= y + RECTANGLE_HEIGHT + radius + 2 && this.y >= y-2) {
			if ((this.x >= x - radius - 2 && this.x <= x - radius + 2)
					|| (this.x <= x + radius + RECTANGLE_WIDTH + 2 && this.x >= x + radius + RECTANGLE_WIDTH - 2)) {
				ans = true;
			} else {
				ans = false;
			}
		}
		return ans;
	}

	public boolean checkUpOrDown(double x, double y) 
	{ //공이 블록의 왼쪽 또는 오른쪽에 부딫힌 경우 
		boolean ans = false;
		if (this.x >= x - 2  && this.x <= x + RECTANGLE_WIDTH + 2) {
			if ((this.y >= y - radius - 2 && this.y <= y - radius + 2)
					|| (this.y <= y + RECTANGLE_HEIGHT + radius + 2 && this.y >= y + RECTANGLE_HEIGHT + radius - 2)) {
				ans = true;
			} else {
				ans = false;
			}
		}
		return ans;
	}
	
	public boolean checkEdge(double x, double y) 
	{ //공이 블록의 왼쪽 또는 오른쪽에 부딫힌 경우 
		boolean ans = false;
		if (this.y >= y - 9  && this.y <= y - 7) {
			if ((this.x >= x - 9 && this.x <= x - 7)
					|| (this.x <= x + RECTANGLE_WIDTH + 9 && this.x >= x + RECTANGLE_WIDTH + 7)) {
				ans = true;
			} else {
				ans = false;
			}
		} else if (this.x >= x - 9  && this.x <= x - 7) {
			if ((this.y >= y - 9 && this.y <= y - 7)
					|| (this.y <= y + RECTANGLE_HEIGHT + 9 && this.y >= y + RECTANGLE_HEIGHT + 7)) {
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
		animation.stop();

	}
	public void reStart() throws InterruptedException {
		for(int i=0;i<BLOCk_ROW;i++) {
			for(int j=0;j<BLOCk_COLUMN;j++) {	
				R[i][j].setVisible(true);
			}
		}
		score = 0;
		block_num = 0;
		dx = 1;
		dy = 1;
		x = (int) (PANE_WIDTH / 2 + radius);
		y = 265;
		
		Bar.setX(300);
		Thread.sleep(500);
		play();
	}

	public DoubleProperty rateProperty() {
		return animation.rateProperty();
	}

	public void moveBall() 
	{
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
		if (block_num == BLOCk_ROW*BLOCk_COLUMN) {
			startMessage.setText("You Win !!");
			animation.pause();
		}
	}
	
	public void checkBlock() 
	{
		
		for(int i=0;i<BLOCk_ROW;i++) {
			for(int j=0;j<BLOCk_COLUMN;j++) {	
				if(R[i][j].isVisible()==false) continue; //이미 처리된 블록은패스
				
				if (checkLeftOrRight(R[i][j].getX(), R[i][j].getY())) {
					//공이 블록의 왼쪽 또는 오른쪽 부딫혔다면 x의 방향을 바꾸어준다.
					dx *= -1;
					R[i][j].setVisible(false);
					score += 10;
					block_num++; //처리된 블록의 개수를 더해준다.
				}
				if (checkUpOrDown(R[i][j].getX(), R[i][j].getY())) {
					//공이 블록의 위쪽 또는 아래에 부딫혔다면 y의 방향을 바꾸어준다.
					dy *= -1;
					R[i][j].setVisible(false);
					score += 10;
					block_num++; //처리된 블록의 개수를 더해준다.
				} 
				if (checkEdge(R[i][j].getX(), R[i][j].getY())) {
					//공이 블록의 위쪽 또는 아래에 부딫혔다면 y의 방향을 바꾸어준다.
					dx *= -1;
					dy *= -1;
					R[i][j].setVisible(false);
					score += 10;
					block_num++; //처리된 블록의 개수를 더해준다.
				}
			}
		}
	}

}
