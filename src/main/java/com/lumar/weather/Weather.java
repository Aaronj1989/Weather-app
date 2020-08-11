package com.lumar.weather;

import java.sql.Date;

public class Weather {

	private String location;

	private String key;

	private String date;

	private int high;

	private int low;

	private String dayPhrase;

	private String nightPhrase;

	private int currentCondition;

	public Weather(String location, String key, String date, int high, int low, String dayPhrase, String nightPhrase) {

		this.location = location;

		this.key = key;
		this.date = date;
		this.high = high;
		this.low = low;
		this.dayPhrase = dayPhrase;
		this.nightPhrase = nightPhrase;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCurrentCondition() {
		return currentCondition;
	}

	public  void setCurrentCondition(int currentCondition) {
		this.currentCondition = currentCondition;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public String getDayPhrase() {
		return dayPhrase;
	}

	public void setDayPhrase(String dayPhrase) {
		this.dayPhrase = dayPhrase;
	}

	public String getNightPhrase() {
		return nightPhrase;
	}

	public void setNightPhrase(String nightPhrase) {
		this.nightPhrase = nightPhrase;
	}

}
