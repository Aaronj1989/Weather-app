package com.lumar.connection;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

	Connection con;

	public Connection getConnection() {
System.out.println("getting connection");
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("sql.properties"));
			// assigning connection values that lead to the specific database
			String dbuser = props.getProperty("user");
			String password = props.getProperty("password");
			String url = props.getProperty("dburl");

			Class.forName("com.mysql.cj.jdbc.Driver");
//have to redeclare connection because this is a new scope.
			con = DriverManager.getConnection(url, dbuser, password);


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
