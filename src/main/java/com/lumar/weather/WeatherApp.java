package com.lumar.weather;

import java.util.ArrayList;

import com.lumar.controller.Scene1Controller;
import com.lumar.rest.ForecastCaller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class WeatherApp extends Application {

	@FXML
	private TextField citySearchField;

	@FXML
	private Button randomizerButton;
	@FXML
	private ImageView giphyImage;

	@FXML
	private Scene1Controller scene1Controller;

	Weather[] forecasts;

	//checks database for data for the current scene
	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene1.fxml"));
		StackPane root = loader.load();

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.setTitle("WeatherR");

		scene1Controller = loader.getController();

		scene1Controller.addScenePaneToList((Pane) root.getChildren().get(0));
		scene1Controller.getForecasts();

		primaryStage.show();

	}

	public static void main(String[] args) {

		launch(args);

	}

}