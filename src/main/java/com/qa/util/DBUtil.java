/**
 * @author Deepak Rai
 */
package com.qa.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Deepak Rai
 *
 */
public class DBUtil {

	private DBUtil() {

	}

	/**
	 * {@summary execute SQL query to fetch data}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 * @throws SQLException
	 */
	public static void executeQuery() {
		
		// database URL
		final String DB_URL = "jdbc:mysql://localhost:3306/telusko";
		// Database credentials
		final String USER = "root";
		final String PASS = "1234";

		// create connection with database
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				// Create a Statement object for sending SQL statements to the database.SQL
				// statements without parameters are normally executed using Statement objects.
				// If the same SQL statement is executed many times, it may be more efficient to
				// use a PreparedStatement object.
				Statement stmt = conn.createStatement();) {

			String sql;
			sql = "SELECT * from student";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String college = rs.getString("college");
				System.out.println(name + " " + college);
			}

			// Clean-up environment
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@summary execute SQL query to update table}
	 * 
	 * @param
	 * @return
	 * @author deepak rai
	 * @throws SQLException
	 */
	public static void executeUpdateQuery() {
		
		final String DB_URL = "jdbc:mysql://localhost:3306/telusko";
		// Database credentials
		final String USER = "root";
		final String PASS = "1234";

		// create connection with database
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				// Create a Statement object for sending SQL statements to the database.SQL
				// statements without parameters are normally executed using Statement objects.
				// If the same SQL statement is executed many times, it may be more efficient to
				// use a PreparedStatement object.
				Statement stmt = conn.createStatement();) {

			// Insert first record
			String record = "INSERT INTO student(name, college)  " + "VALUES ('Pankaj','BHU')";
			stmt.executeUpdate(record); // SQL DDL statement

			System.out.println("Records inserted");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}