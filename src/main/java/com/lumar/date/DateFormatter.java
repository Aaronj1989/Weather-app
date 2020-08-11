package com.lumar.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.lumar.weather.Weather;

public class DateFormatter {

	public static String formatDate(String dateToFormat) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(dateToFormat);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		int month = calendar.get(Calendar.MONTH);
		String result = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + "  "
				+ ++month + "/" + calendar.get(Calendar.DAY_OF_MONTH);

		return result;
	}
	
	//format todays date to match and see if it is equal to the string date passed in
	
	public Boolean isUpdated(String weatherDate) {
		
		System.out.println(weatherDate);
		boolean updated = true;
		
		Calendar today = Calendar.getInstance();
		Date date = today.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String todaysDate = formatter.format(date);

		String calendarDate = formatDate(todaysDate);
		
		if(!calendarDate.equals(weatherDate)){
			
			updated = false;
			
		}
		return updated;
}
	
	public static void formatDates(Weather[] forecasts) {

		for (Weather w : forecasts) {

			w.setDate(DateFormatter.formatDate(w.getDate()));
		}
	}
}
