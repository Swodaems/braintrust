package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static boolean testMode = false;
	public static Connection getConnection() {
		
		// First time we will use a literal user and password
		// and we will refactor to use environment variables for safety
		// in practice you should NEVER use literals
		
		// Note: JDBC url has a specific format
		//  jdbc:database-type://network-location:port/internal-database
		String url = "jdbc:postgresql://localhost:5432/postgres";
		try {
			Connection conn = DriverManager.getConnection(url, 
								System.getenv("BTU"), 
								System.getenv("BTP"));
			
					return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to connect to database. Sad :(");
			return null;
		}
	}

}
