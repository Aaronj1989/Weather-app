package com.lumar.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lumar.weather.Weather;

public class WeatherDAO {

	private Connection con;

	// search database for last 5 day forecast for given location and scene.
	public Weather[] getForecasts(int sceneNum) {

		String query = "Select * from (Select * from forecasts where Scene = " + sceneNum + " ORDER BY id DESC LIMIT 5)"
				+ " sub ORDER BY id ASC";
		Weather[] forecasts = new Weather[5];

		con = new DBConnector().getConnection();

		try {

			Statement statement = con.createStatement();

			ResultSet rs = statement.executeQuery(query);

			if (!rs.isBeforeFirst()) {

				return null;
			}
			int index = 0;
			while (rs.next()) {
				String location = rs.getString("location");

				String key = rs.getString("location_key");
				String date = rs.getString("location_date");
				int high = rs.getInt("tempHigh");
				int low = rs.getInt("tempLow");
				String dayPhrase = rs.getString("dayPhrase");
				String nightPhrase = rs.getString("nightPhrase");
				int currentCondition = rs.getInt("current_conditions");
				Weather weatherObj = new Weather(location, key, date, high, low, dayPhrase, nightPhrase);
				weatherObj.setCurrentCondition(currentCondition);
				forecasts[index++] = weatherObj;

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return forecasts;
	}

	public void saveForecasts(Weather[] weeklyForecast, int scene) {
		String query;

		PreparedStatement ps = null;
		con = new DBConnector().getConnection();
		try {
			query = "Insert into forecasts(location,location_key,location_date,tempHigh,tempLow,dayPhrase,nightPhrase,scene, current_conditions) "
					+ "values(?,?,?,?,?,?,?,?,?)";

			ps = con.prepareStatement(query);

			for (Weather w : weeklyForecast) {

				ps.setString(1, w.getLocation());
				ps.setString(2, w.getKey());
				ps.setString(3, w.getDate());
				ps.setInt(4, w.getHigh());
				ps.setInt(5, w.getLow());
				ps.setString(6, w.getDayPhrase());
				ps.setString(7, w.getNightPhrase());
				ps.setInt(8, scene);
				ps.setInt(9, w.getCurrentCondition());

				ps.execute();
			}

		} catch (SQLException ex) {

			ex.printStackTrace();

		} finally {

			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
