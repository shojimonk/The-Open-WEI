package openWEI;

import java.util.List;
import java.util.Properties;
import java.util.ArrayList;
import java.sql.*;

/// Class used to communicate with remote database. Utilizes JDBC API. 
/// Builds SQL queries and commands, and parses query results before returning them to the calling object.
public class Database_Communications {
	
	private Connection conn;
	
	/// Class constructor. Shouldn't need to do anything.
	public Database_Communications()
	{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("******** Error with postgres driver? *********");
			e.printStackTrace();
		}
	}
	
	/// Checks for a database at the given host:port location.
	/// Output: boolean representing presence of database.
	public Boolean checkForHost(String hostPort) throws SQLException
	{
		
		Properties connProperties = new Properties();
		connProperties.setProperty("user", "username");
		connProperties.setProperty("password", "myPass");
		connProperties.setProperty("ssl", "true");
		String url = "jdbc:postgresql://" +hostPort+"/ohmbaseopenwei";
		
		try{
			conn = DriverManager.getConnection(url, connProperties);
		}
		catch(SQLException ex){
			System.out.println("failed to connect...");
			return false;
		}
		
		//conn.
		
		return true;
	}
	
	/// Builds query to check database for login matching the given value.
	/// Output: boolean representing if there was a successful match.
	public Boolean checkLogin(String login)
	{
		
		
		return true;
	}
	
	/// Builds an SQL select statement with the given search string. Parses
	/// results into a list of strings
	/// output: list of strings. each entry corresponds to one row of the results.
	public ResultSet sendQuery(String searchString)
	{
		ResultSet results = null;
		
		return results;
		
	}

	public Boolean newEntry(String compType, String values)
	{
		
		
		return true;
	}
	
}
