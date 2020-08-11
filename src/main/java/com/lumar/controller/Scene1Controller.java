package com.lumar.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.lumar.weather.Weather;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Scene1Controller extends Controller {

	@FXML
	private AnchorPane scene1Pane;
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
	@FXML
	private StackPane stackPane;

	private FXMLLoader scene2Loader;
	
	public Scene1Controller() {
		super.setSceneNumber(1);
	}

	// get next Scene method. if pane was already loaded once while program running
	// just retrieve pane already stored in the list
	@FXML
	public void getNextScene(ActionEvent event) throws IOException {

		Parent scene2Pane = scenePaneList.size() < 2 ? loadScene2FromFXML() : scenePaneList.get(1);

		Scene scene = nextSceneButton.getScene();

		if (!(scene2Pane instanceof StackPane)) {
			stackPane = (StackPane) scene.getRoot();
		}

		scene2Pane.translateXProperty().set(scene.getWidth());

		stackPane.getChildren().add(scene2Pane);

		Timeline timeline = new Timeline();

		KeyValue kv = new KeyValue(scene2Pane.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
		timeline.getKeyFrames().add(kf);

		// set on finished
		timeline.setOnFinished(event1 -> {

			if (scenePaneList.size() < 2) {
				scene2Controller.getForecasts();
				addScenePaneToList((Pane) scene2Pane);

			}

			stackPane.getChildren().remove(scene1Pane);

		});
		timeline.play();

	}

	public Pane loadScene2FromFXML() throws IOException {

		scene2Loader = new FXMLLoader(getClass().getResource("/Scene2.fxml"));
		AnchorPane scene2Pane = scene2Loader.load();
		scene2Controller = scene2Loader.getController();

		return scene2Pane;
	}

}
