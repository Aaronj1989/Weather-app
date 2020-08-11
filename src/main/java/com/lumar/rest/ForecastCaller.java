package com.lumar.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lumar.controller.Controller;
import com.lumar.weather.Weather;

import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class ForecastCaller extends Task<String> {

	private Map<String, String> locationMap;

	private TextField locationField;

	private String location;

	private Weather weatherArr[];

	private Controller controller;

	public ForecastCaller() {
	}

	public ForecastCaller(TextField locationField, String location, Controller controller) {

		this.locationField = locationField;
		locationMap = new HashMap<String, String>();
		this.location = location;
		this.controller = controller;
	}

	public ForecastCaller(String location) {
		this.location = location;
	}

	// auto complete feature when user searches(calls) for a list of
	// locations seen in drop down menu.
	@Override
	protected String call() {

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(
				"http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=JbbGAVd4HFZti1d4HbmrS8kqRtnPKYpg&q="
						+ location)
				.get().build();
		ResponseBody response = null;

		try {

			response = client.newCall(request).execute().body();

			String responseString = response.string();
	
			JSONArray jArr = new JSONArray(responseString);

			for (int i = 0; i < jArr.length(); i++) {

				JSONObject loc = (JSONObject) jArr.get(i);

				locationMap.put(
						loc.getString("LocalizedName") + ", "
								+ loc.getJSONObject("AdministrativeArea").getString("ID"),
						loc.getString("Key"));
			}

			AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(locationField,
					locationMap.keySet());

			autoCompletionBinding.setOnAutoCompleted(e -> {
				

				// prompt asks if you want to overwrite
				if (!controller.getLocationLabel().equals("Location")) {

					// prompt user for questioning to overwrite
					Optional<ButtonType> option = this.getConfirmation();
					if (option.get() == ButtonType.CANCEL) {
						return;
					}else {
						controller.clearLabels();
					}
				}

				
				location = e.getCompletion();
				this.getForecastData(locationMap.get(e.getCompletion()));

				locationField.clear();
				
				controller.setForecasts(weatherArr);
				controller.setUpScene();

			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * gets 5 day forecast and puts it into an array and gets current conditions
	 * 
	 * @param locationKey
	 */
	public void getForecastData(String locationKey) {

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("http://dataservice.accuweather.com/forecasts/v1/daily/5day/"
				+ locationKey + "?apikey=JbbGAVd4HFZti1d4HbmrS8kqRtnPKYpg").get().build();
		ResponseBody response = null;

		try {

			response = client.newCall(request).execute().body();

			String responseString = response.string();

			JSONObject jsnObject = new JSONObject(responseString);

			JSONArray jArr = jsnObject.getJSONArray("DailyForecasts");

			weatherArr = new Weather[5];

			for (int i = 0; i < jArr.length(); i++) {

				JSONObject forecast = (JSONObject) jArr.get(i);

				String date = forecast.getString("Date");

				int minTemp = forecast.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value");
				int maxTemp = forecast.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value");

				String dayPhrase = forecast.getJSONObject("Day").getString("IconPhrase");

				String nightPhrase = forecast.getJSONObject("Night").getString("IconPhrase");

				Boolean hasPrecipitation = forecast.getJSONObject("Day").getBoolean("HasPrecipitation");

				weatherArr[i] = new Weather(location, locationKey, date, maxTemp, minTemp, dayPhrase, nightPhrase);

			}

			weatherArr[0].setCurrentCondition(getCurrentCondition(locationKey));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public int getCurrentCondition(String locationKey) {

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("http://dataservice.accuweather.com/currentconditions/v1/"
				+ locationKey + "?apikey=JbbGAVd4HFZti1d4HbmrS8kqRtnPKYpg").get().build();
		ResponseBody response = null;

		int currentTemp = 0;

		try {
			response = client.newCall(request).execute().body();

			String responseString = response.string();

			JSONArray jsonArr = new JSONArray(responseString);

			JSONObject jsonObject = jsonArr.getJSONObject(0);

			JSONObject temperature = jsonObject.getJSONObject("Temperature");

			currentTemp = temperature.getJSONObject("Imperial").getInt("Value");

		

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return currentTemp;
	}

	public Optional<ButtonType> getConfirmation() {

		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle("Confirmation");
		a.setContentText("Are you sure you want to overwrite this location?");
	
		Optional<ButtonType> option = a.showAndWait();
		return option;
	}

	public Weather[] getForecasts() {

		return this.weatherArr;
	}

}
