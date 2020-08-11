package com.lumar.rest;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.ResponseBody;

public class GiphyCaller {

	String weatherStatus;
	
	String giphyUrl;
	
	public GiphyCaller(String weatherStatus) {

		if(weatherStatus.contains("w/")) {
			
			weatherStatus = weatherStatus.replace("w/","with");
		}
			
		this.weatherStatus = weatherStatus + " weather";
	}
	
	

       public void call(){
    	   
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder().url("https://api.giphy.com/v1/gifs/random?api_key=0fo0qq8t0ocZGM3c0MUcJkPu0AVaCV1w&tag=" +  weatherStatus).get().build();
		
		ResponseBody response = null;
		
		try{
			response = client.newCall(request).execute().body();
			
			System.out.println(response);
			String responseString = response.string();
			
			JSONObject jsnObj = new JSONObject(responseString);
			
			this.giphyUrl = jsnObj.getJSONObject("data").getJSONObject("images").getJSONObject("fixed_height_small").getString("url");
				
			formatUrl(giphyUrl); 
			System.out.println("returning giphyURL to imageview" + giphyUrl);
		}catch(IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void formatUrl(String urlString){
		
		String patternToFind = "https://media\\d+";
		Pattern p = Pattern.compile(patternToFind);
		Matcher m = p.matcher(urlString);

		urlString = m.replaceFirst("http://i");
	
		setGiphyUrl(urlString);
	}
	
	public String getGiphyUrl() {
	return this.giphyUrl;
	}
	
	public void setGiphyUrl(String giphyUrl) {
		this.giphyUrl = giphyUrl;
	}

}
