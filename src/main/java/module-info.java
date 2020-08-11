module Weather {

	exports com.lumar.connection;
	exports com.lumar.controller;
	exports com.lumar.weather;
	exports com.lumar.rest;
  
    requires java.sql;
	requires java.ws.rs;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.web;
	requires okhttp3;
	requires org.controlsfx.controls;
	requires org.json;
	requires org.junit.jupiter.api;


	


	


	
	opens com.lumar.controller;
	

}