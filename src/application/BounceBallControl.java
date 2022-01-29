package application;

import javafx.application.*;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.*;

public class BounceBallControl extends Application {
	@Override
	public void start(Stage primaryStage) {
		BallPane ballPane = new BallPane();
		
		
		ballPane.setOnMouseClicked(e->{
			ballPane.play();
		});
		
		Scene scene = new Scene(ballPane, 450, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("BounceBall");
		primaryStage.show();
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
