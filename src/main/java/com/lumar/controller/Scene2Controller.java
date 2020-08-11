package com.lumar.controller;

import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Scene2Controller extends Controller {

	@FXML
	private AnchorPane scene2Pane;

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
	@FXML
	private Button previousSceneButton;
	// get previous scene method
	@FXML
	private Scene3Controller scene3Controller;

	FXMLLoader scene3Loader;

	
	public Scene2Controller() {
		super.setSceneNumber(2);
	}
	public void getPreviousScene() throws IOException {

		Scene scene = previousSceneButton.getScene();

		AnchorPane scene1Pane = (AnchorPane) scenePaneList.get(0);

		StackPane parentContainer = (StackPane) scene.getRoot();

		scene1Pane.translateXProperty().set(-scene.getWidth());

		parentContainer.getChildren().add(scene1Pane);

		Timeline timeline = new Timeline();

		KeyValue kv = new KeyValue(scene1Pane.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
		timeline.getKeyFrames().add(kf);

		timeline.setOnFinished(event1 -> {
			parentContainer.getChildren().remove(scene2Pane);
			// System.out.println("now back at scene " + scene1Controller.getSceneNumber());
		});
		timeline.play();

	}

	// get next Scene method.
	@FXML
	public void getNextScene(ActionEvent event) throws IOException {

		Parent scene3Pane = scenePaneList.size() < 3 ? loadScene3FromFXML() : scenePaneList.get(2);

		Scene scene = nextSceneButton.getScene();

		scene3Pane.translateXProperty().set(scene.getWidth());

		StackPane stackPane = (StackPane) scene.getRoot();

		stackPane.getChildren().add(scene3Pane);

		Timeline timeline = new Timeline();

		KeyValue kv = new KeyValue(scene3Pane.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
		timeline.getKeyFrames().add(kf);

		timeline.setOnFinished(event1 -> {

			if (scenePaneList.size() < 3) {
				scene3Controller.getForecasts();
				addScenePaneToList((Pane) scene3Pane);
			}

			stackPane.getChildren().remove(scene2Pane);

			System.out.println(scene3Controller.getLocationLabel());
		});
		timeline.play();

	}


	public Pane loadScene3FromFXML() throws IOException {

		scene3Loader = new FXMLLoader(getClass().getResource("/Scene3.fxml"));
		AnchorPane scene3Pane = scene3Loader.load();
		scene3Controller = scene3Loader.getController();
		return scene3Pane;
	}
}
