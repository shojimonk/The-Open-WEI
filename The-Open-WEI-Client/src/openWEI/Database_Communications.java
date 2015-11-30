package openWEI;

import java.util.List;
import java.util.ArrayList;

/// Class used to communicate with remote database. Utilizes JDBC API. 
/// Builds SQL queries and commands, and parses query results before returning them to the calling object.
public class Database_Communications {
	
	
	/// Class constructor. Shouldn't need to do anything.
	public Database_Communications()
	{
		
	}
	
	/// Checks for a database at the given host:port location.
	/// Output: boolean representing presence of database.
	public Boolean checkForHost(String hostPort)
	{
		
		
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
	public List<String> sendQuery(String searchString)
	{
		List<String> results = new ArrayList<String>();
		
		return results;
		
	}

}
