package com.lumar.rest;

import static org.junit.jupiter.api.Assertions.*;
import com.lumar.rest.*;

import org.junit.jupiter.api.*;

class GiphyCallerTest {

	GiphyCaller giphyCaller;

	@BeforeEach
	public void init() {

		String weatherStatus = "cloudy w/ sunshine";
		giphyCaller = new GiphyCaller(weatherStatus);

		String url = "https://media0.giphy.com/media/odBk4aiuAiwxi/100.gif?cid=ca261753b679d475b2f489b3c3c30a2b5bf40d4b236027fb&rid=100.gif";
		giphyCaller.formatUrl(url);
	}

	@Test
	void formatUrlTest() {

		assertEquals(
				"http://i.giphy.com/media/odBk4aiuAiwxi/100.gif?cid=ca261753b679d475b2f489b3c3c30a2b5bf40d4b236027fb&rid=100.gif",
				giphyCaller.getGiphyUrl());

	}

}
