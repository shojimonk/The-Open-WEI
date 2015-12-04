package openWEI;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import jBCrypt.BCrypt;

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
		connProperties.setProperty("user", "Shoji");
		connProperties.setProperty("password", "Delicious1");
		//connProperties.setProperty("ssl", "true");
		String url = "jdbc:postgresql://" +hostPort+"/ohmbaseopenwei";
		
		try{
			conn = DriverManager.getConnection(url, connProperties);
		}
		catch(SQLException ex){
			System.out.println("failed to connect to: "+url);
			return false;
		}
		
		
		return true;
	}
	
	/// Builds query to check database for login matching the given value.
	/// Output: boolean representing if there was a successful match.
	public Boolean checkLogin(String[] login)
	{
		ResultSet results = null;
		Statement stmnt = null;
		String searchable = "select * from users.account where username like '" + login[0] + "';";
		System.out.println(searchable);
		try{
			stmnt = conn.createStatement();
			results = stmnt.executeQuery(searchable);
			while(results.next()){
				String returnedPass = results.getString(2);
				System.out.println("returned pass: " + returnedPass);
				System.out.println("given pass: " + login[1]);
				if (BCrypt.checkpw(login[1], returnedPass)){ return true; }
			}
		}
		catch(SQLException ex){
			System.out.println("Error with select.");
			ex.printStackTrace();
		}
		
		return false;
	}
	
	/// Builds an SQL select statement with the given search string. Parses
	/// results into a list of strings
	/// output: list of strings. each entry corresponds to one row of the results.
	public ResultSet sendQuery(String[] searchString)			// changed from returning ArrayList<ArrayList<String>>
	{
		ResultSet results = null;
		Statement stmnt = null;
		//ArrayList<ArrayList<String>> parsedResults = new ArrayList<ArrayList<String>>();
		
		String searchable = "select name, notes, quantity, last_modified, spec_sheets, location from " + 
				searchString[0] + " where "+ searchString[0] + ".name like '%" + searchString[1] + "%';";
		System.out.println(searchable);
		
		try{
			stmnt = conn.createStatement();
			results = stmnt.executeQuery(searchable);
			/*
			while(results.next())
			{
				ArrayList<String> tempStore = new ArrayList<String>();
				tempStore.add(results.getString("name"));
				tempStore.add(results.getString("notes"));
				
				int intQuantity = results.getInt("quantity");
				tempStore.add(String.format("%d", intQuantity));
				
				Date dateDate = results.getDate("last_modified");
				tempStore.add(dateDate.toString());
				
				tempStore.add(results.getString("spec_sheets"));
				tempStore.add(results.getString("location"));
				
				parsedResults.add(tempStore);
			}
			*/
			//stmnt.close();
		}
		catch(SQLException ex){
			System.out.println("Error with select.");
			ex.printStackTrace();
		}
		return results;
	}

	public Boolean newEntry(String compType, String values)
	{
		
		
		return true;
	}
	
	public List<String> getTables()
	{
		List<String> columns = new ArrayList<String>();
		try {
			DatabaseMetaData forTables = conn.getMetaData();
			ResultSet tableNames = forTables.getTables(null, "public", "%", null);
			while(tableNames.next()){
				columns.add(tableNames.getString(3));
			}
			
		} catch (SQLException e) {
			System.out.println("Error getting tables metadata.");
			e.printStackTrace();
		}
		
		return columns;
	}
}
