package com.lumar.controller;


import java.util.ArrayList;

import com.lumar.connection.WeatherDAO;
import com.lumar.date.DateFormatter;
import com.lumar.rest.ForecastCaller;
import com.lumar.rest.GiphyCaller;
import com.lumar.weather.Weather;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Controller {

	@FXML
	protected TextField citySearchField;

	protected ForecastCaller caller;

	private Weather[] forecasts;

	@FXML
	private ImageView giphyImage;

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
	private ListView<String> forecastListView;
	@FXML
	private Label dateLabel;

	protected int sceneNum;

	static protected ArrayList<Pane> scenePaneList = new ArrayList<Pane>();

	public ArrayList<Pane> getScenePaneList() {
		return scenePaneList;
	}

	public void setScenePaneList(ArrayList<Pane> scenePaneList) {
		Controller.scenePaneList = scenePaneList;
	}

	public void checkCharCount() {
		String text = citySearchField.getText();

		text = text.trim();
		int textLength = text.replace(" ", "").length();

		if (textLength > 2) {

			text = text.replace(" ", "%20");

			callClient(text);
		}
	}

	/** calls api for list of locations */
	public void callClient(String location) {

		caller = new ForecastCaller(citySearchField, location, this);
		Thread thread = new Thread((Runnable) caller);

		thread.start();
	}

	// formats the dates and saves them to the database
	public void setUpScene() {

		if (forecasts == null) {

			return;
		}

		DateFormatter.formatDates(forecasts);

		setLabels(forecasts);
       
		WeatherDAO weatherDAO = new WeatherDAO();

		weatherDAO.saveForecasts(forecasts, getSceneNumber());
		
		setGif(forecasts[0].getDayPhrase());
	}

	public int getSceneNumber() {
		return this.sceneNum;
	}
	
	public void setSceneNumber(int num) {
		this.sceneNum = num;
	}


	public String getLocationLabel() {
		return this.locationLabel.getText();

	}

	public void setLocationLabel(String location) {
		locationLabel.setText(location);
	}

	public void setLabels(Weather[] forecasts) {

		
		locationLabel.setText(forecasts[0].getLocation());

		dateLabel.setText(forecasts[0].getDate());

		dayPhrase.setText(dayPhrase.getText() + " " + forecasts[0].getDayPhrase());
		nightPhrase.setText(nightPhrase.getText() + " " + forecasts[0].getNightPhrase());
		highTempLabel.setText(highTempLabel.getText() + " " + Integer.toString(forecasts[0].getHigh()));
		lowTempLabel.setText(lowTempLabel.getText() + " " + Integer.toString(forecasts[0].getLow()));
		currentCondition.setText(Integer.toString(forecasts[0].getCurrentCondition()));
	
		for (Weather w : forecasts) {



			forecastListView.getItems().add(w.getDate() + "     " + w.getHigh() + "/" + w.getLow());
		}

	}

	// gets last known forecasts from database and makes sure the retrieved forecast
	// is up
	// to date before setting labels and then sets gif image
	public void getForecasts() {

		WeatherDAO weatherDao = new WeatherDAO();

		forecasts = weatherDao.getForecasts(this.getSceneNumber());

		if (forecasts == null) {
			return;
		}

		isUpdated(forecasts[0]);

		setLabels(forecasts);

		setGif(forecasts[0].getDayPhrase());
	}

	// if weather forecast isn't up to date call api and update the lables to
	// current forecast.

	public void isUpdated(Weather forecast) {

		DateFormatter dateFormatter = new DateFormatter();

		Boolean updated = dateFormatter.isUpdated(forecast.getDate());

		if (!updated) {

			ForecastCaller forecastCaller = new ForecastCaller(forecast.getLocation());

			forecastCaller.getForecastData(forecast.getKey());

			forecasts = forecastCaller.getForecasts();

			DateFormatter.formatDates(forecasts);

			new WeatherDAO().saveForecasts(forecasts, getSceneNumber());

		}

	}

	public void addScenePaneToList(Pane pane) {

		scenePaneList.add(pane);

	}

	public void setForecasts(Weather[] forecasts) {
		this.forecasts = forecasts;
	}

	public void clearLabels() {
		locationLabel.setText(forecasts[0].getLocation());

		dateLabel.setText("Date: ");

		dayPhrase.setText("Today: ");
		nightPhrase.setText("Tonight: ");
		highTempLabel.setText("High: ");
		lowTempLabel.setText("Low: ");
		currentCondition.setText("");
		forecastListView.getItems().clear();

	}
      
	public void setGif(String conditionPhrase) {

		GiphyCaller gc = new GiphyCaller(conditionPhrase);
		gc.call();

		String url = gc.getGiphyUrl();


		Image image = new Image(url);
		    
	
		if(image.heightProperty().intValue() == 0) {
	
			setGif(conditionPhrase);
			return;
		}
	
		giphyImage.setImage(image);

	}
}
