package com.lumar.controller;

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Scene3Controller extends Controller {
@FXML
	private AnchorPane scene3Pane;
@FXML
private Button previousSceneButton;

@FXML
private AnchorPane scenePane;
@FXML
private Scene2Controller scene2Controller;

@FXML
private Label locationLabel;

@FXML
private Label highTempLabel;

@FXML
private Label lowTempLabel;

@FXML
private Label nightPhrase;

@FXML
private Label dayPhrase;

@FXML
private Label currentCondition;

@FXML
private ImageView giphyImage;

@FXML
private ListView<String> forecastListView;
@FXML
private Label dateLabel;
@FXML
private TextField citySearchField;
@FXML
private Button nextSceneButton;


public Scene3Controller() {
	super.setSceneNumber(3);
}


	@FXML
	public void getPreviousScene() throws IOException {
		Parent root = scenePaneList.get(1);

		Scene scene = previousSceneButton.getScene();
       
		
		root.translateXProperty().set(-scene.getWidth());

		StackPane stackPane =  (StackPane) scene.getRoot();
		
		stackPane.getChildren().add(root);
		
		Timeline timeline = new Timeline();
		
		KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
		timeline.getKeyFrames().add(kf);
		
		timeline.setOnFinished(event1->{
			
			stackPane.getChildren().remove(scene3Pane);
		});
		timeline.play();

	}

	

}
