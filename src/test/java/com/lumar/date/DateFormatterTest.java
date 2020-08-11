package com.lumar.date;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateFormatterTest {
DateFormatter dateFormatter;
	@BeforeEach
      void init() {
dateFormatter = new DateFormatter();
	}
	
	@Test
	void dateFormatTest() {
		String date = "2020-04-07T07:00:00-05:00";

		
		assertEquals("Tue  4/7", DateFormatter.formatDate(date));

	}

	
	@Test
	void dateUpdatedTest() {
		
		String date = "Fri  4/10";
		
		assertEquals(true, dateFormatter.isUpdated(date));
	}
}
