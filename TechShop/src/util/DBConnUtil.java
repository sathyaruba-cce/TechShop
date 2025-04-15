package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.DatabaseConnectionException;

public class DBConnUtil {
	private static final String fileName = "db.properties";

	public static Connection getDbConnection() throws DatabaseConnectionException {
		Connection con = null;
		String connString = null;

		try {
			connString = DBPropertyUti.getConnectionString(fileName);
		} catch (IOException e) {
			throw new DatabaseConnectionException("Connection String Creation Failed", e);
		}

		if (connString != null) {
			//System.out.println("Connection String: " + connString); 
			try {
				con = DriverManager.getConnection(connString);
			} catch (SQLException e) {
				throw new DatabaseConnectionException("Error While Establishing DBConnection........", e);
			}
		}

		return con;
	}
}
