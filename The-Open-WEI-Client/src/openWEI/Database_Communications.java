package openWEI;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import jBCrypt.BCrypt;
import java.sql.*;

/*
 *  Class used to communicate with remote database. Utilizes JDBC API.
 *  Builds SQL queries and commands, and parses query results before returning them to the calling object. 
 */
public class Database_Communications {
	
	private Connection conn;
	
	/*
	 * Class constructor. Sets database type to PostgreSQL.
	 * Throws error if PostgreSQL driver not available.
	 */
	public Database_Communications()
	{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("******** Error with postgres driver *********");
			e.printStackTrace();
		}
	}
	
	/*
	 * Checks for a database at the given host:port location.
	 * Output: boolean representing presence of database.
	 */
	public Boolean checkForHost(String hostPort) throws SQLException
	{
		
		Properties connProperties = new Properties();
		connProperties.setProperty("user", "postgres");
		connProperties.setProperty("password", "pandabear");
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
	
	/*
	 *  Builds query to check database for login matching the given value.
	 *  Output: boolean representing if there was a successful match.
	 */
	public Boolean checkLogin(String[] login)
	{
		ResultSet results = null;
		Statement stmnt = null;
		String searchable = "select username, password from users.account where username like '" + login[0] + "';";
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
	
	/*
	 *  Builds an SQL select statement with the given search string. Parses results into a list of strings
	 *  output: SQL ResultSet that contains all the search results.
	 */ 
	public ResultSet sendQuery(String[] search)
	{
		ResultSet results = null;
		//Statement stmnt = null;
		//String searchable = "select ID, name, notes, quantity, last_modified, spec_sheets, location from " + 
		//		searchString[0] + " where "+ searchString[0] + ".name like '%" + searchString[1] + "%';";
		//System.out.println(searchable);
		
		try{
			PreparedStatement ps = conn.prepareStatement("SELECT ID, name, notes, quantity, last_modified, spec_sheets, location from "+ search[0] +" where "+ search[0] +".name like ? ;");
			//ps.setString(1, search[0]);
			//ps.setString(2, search[0]);
			ps.setString(1, "%" + search[1] + "%");
			results = ps.executeQuery();
			//stmnt = conn.createStatement();
			//results = stmnt.executeQuery(searchable);
		}
		catch(SQLException ex){
			System.out.println("Error with select.");
			ex.printStackTrace();
		}
		return results;
	}

	/*
	 * 
	 */
	public boolean modifyData(List<List<String>> updates){
		
		for(List<String> each : updates){
			try {
				PreparedStatement ps;
				if((each.get(1).equals("quantity")) && (!each.get(0).equals("other"))){
					ps = conn.prepareStatement("UPDATE "+ each.get(0) +" SET "+ each.get(1) +" = ? WHERE ID = "+ each.get(3) +" ;");
					ps.setInt(1, Integer.parseInt(each.get(2)));
				}else{
					ps = conn.prepareStatement("UPDATE "+ each.get(0) +" SET "+ each.get(1) +" = ? WHERE ID = "+ each.get(3) +" ;");
					ps.setString(1, each.get(2));
				}
				/*
				ps.setString(1, each.get(0));
				ps.setString(2, each.get(1));
				ps.setString(3, each.get(2));
				ps.setString(4, each.get(3));
				*/
				System.out.println("Query is: " + ps.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println("failed to build prepared statement.");
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/*
	 * For adding a new entry to the database in the chosen table.
	 */
	public boolean newEntry(String compType, String values)
	{
		
		
		return true;
	}
	
	public List<String> getTables()
	{
		List<String> columns = new ArrayList<String>();
		try {
			DatabaseMetaData forTables = conn.getMetaData();
			String[] types = {"TABLE"};
			ResultSet tableNames = forTables.getTables(null, "public", "%", types);
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
