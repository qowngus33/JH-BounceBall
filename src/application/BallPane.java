package application;

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
	private double x = 100, y = 100;
	private double dx = 1, dy = 1;
	private Circle circle = new Circle(x, y, radius);
	private Text startMessage = new Text(150,50,"Click Anywhere To Start!!");
	private Rectangle rectangle = new Rectangle(200,300,60,15);
	private Timeline animation;

	public BallPane() {
		circle.setFill(Color.GREEN);
		rectangle.setFill(Color.BROWN);
		
		getChildren().add(circle);
		getChildren().add(rectangle);
		getChildren().add(startMessage);

		animation = new Timeline(new KeyFrame(Duration.millis(5), e -> moveBall()));
		animation.setCycleCount(Timeline.INDEFINITE);
		
		rectangle.setOnMouseDragged(e->{
			if(e.getX()>0&&e.getX()<450)
				rectangle.setX(e.getX()-7);
			
	    });
		
		
	}
	
	public void play() {
		animation.play();
	}

	public void setText() {
		startMessage.setText("");
	}
	
	public void setTextDead() {
		startMessage.setText("You Are Dead");
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
		if (y < radius || y > getHeight() - radius) {
			dy *= -1;
		}

		if (x >= rectangle.getX()-radius && x <= rectangle.getX()+rectangle.getWidth() + radius)
			if (y >= 290 && y <= 295) {
				dy *= -1;
		}
		
		x += dx;
		y += dy;
		circle.setCenterX(x);
		circle.setCenterY(y);
		
		if(y>=getHeight()-radius-5)
		{
			setTextDead();
			animation.pause();
		}
	}
	

}
