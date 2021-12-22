package com.wallet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionProvider {
	
	static Connection connection;
	
	private DbConnectionProvider() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String jdbcUrl = System.getenv("JDBC_URL");  
			if (jdbcUrl == null) {
				jdbcUrl = "jdbc:mysql://localhost:3306/wallet?allowMultiQueries=true&sessionVariables=sql_mode='PIPES_AS_CONCAT'";
			}
			
			String jdbcUser = System.getenv("JDBC_USER");
			if (jdbcUser == null) {
				jdbcUser = "root";
			}

			String jdbcPass = System.getenv("JDBC_PASS");
			if (jdbcPass == null) {
				jdbcPass = "root";
			}

			 connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
			//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wallet?allowMultiQueries=true&sessionVariables=sql_mode='PIPES_AS_CONCAT'","root", "root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		if (connection == null) {
			new DbConnectionProvider();
		}
		return connection;
	}
	
	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
