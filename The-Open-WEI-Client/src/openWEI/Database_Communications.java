package openWEI;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import jBCrypt.BCrypt;
import java.sql.*;

/**
 * Class used to communicate with remote database. Utilizes JDBC API.
 * Builds SQL queries and commands, and parses query results before returning them to the calling object.
 * @author ShojiStudios
 *
 */
public class Database_Communications {
	
	private Connection conn;
	
	/**
	 * Class constructor. Sets database type to PostgreSQL.
	 * Catches error if PostgreSQL driver not available.
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
	
	/**
	 * Checks for a database at the given host:port location.
	 * @param hostPort String containing hostAddress:port of the database to connect to.
	 * @return Boolean representing presence of database.
	 * @throws SQLException If no database at given host:port location.
	 */
	public Boolean checkForHost(String hostPort) throws SQLException
	{
		
		Properties connProperties = new Properties();
		connProperties.setProperty("user", "username");
		connProperties.setProperty("password", "password");
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
	
	/**
	 * Builds query to check database for login matching the given value.
	 * @param login String Array containing username and password. 
	 * @return boolean representing if there was a successful match.
	 */
	public Boolean checkLogin(String[] login)
	{
		ResultSet results = null;
		String searchable = "select username, password from users.account where username = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(searchable);
			ps.setString(1, login[0]);
			results = ps.executeQuery();
			while(results.next()){
				String returnedPass = results.getString(2);
				if (BCrypt.checkpw(login[1], returnedPass)){ return true; }
			}
		} catch (SQLException e) {
			System.out.println("Error logging in.");
			e.printStackTrace();
		}		
		return false;
	}
	
	/**
	 * Builds an SQL select statement with the given search string. Parses results into a list of strings
	 * @param search String array containing user search terms and table to search. 
	 * @return SQL ResultSet that contains all the search results.
	 */
	public ResultSet sendQuery(String[] search)
	{
		ResultSet results = null;
		
		try{
			PreparedStatement ps = conn.prepareStatement("SELECT ID, name, notes, quantity, last_modified, spec_sheets, location from "+ search[0] +" where "+ search[0] +".name ilike ? ;");
			ps.setString(1, "%" + search[1] + "%");
			results = ps.executeQuery();
		}
		catch(SQLException ex){
			System.out.println("Error with select.");
			ex.printStackTrace();
		}
		return results;
	}

	/**
	 * Takes list of user updates, and iterates through them, applying all updates to the database.
	 * @param updates List of List of Strings, where each list holds table name, column name, modified value, and the row ID.
	 * @return boolean representing success for all updates, or failure for at least one.
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
	
	/**
	 * For adding a new entry to the database in the chosen table.
	 * @param compType The table to add the new entry to.
	 * @param values The values for the new entry
	 * @return Boolean success or failure of addition of new data.
	 */
	public boolean newEntry(String compType, List<String> fields, List<String> values)
	{
		try {
			int index = fields.indexOf("location");
			if((index != -1) && (values.get(index).equals(""))){
				System.out.println("no location given.");
				values.set(index, "Unsorted");
			}
			int fldCount = fields.size();						// Start defining insert statement
			String sttmnt = "insert into " + compType + " (" + fields.get(0);
			for(int i = 1; i < fldCount; i++){				
				sttmnt = sttmnt.concat(", " + fields.get(i));	// "fields" is built entirely by us, so no concern of injection attacks
			}
			sttmnt = sttmnt.concat(") values (?");
			for(int i = 1; i < fldCount; i++){
				sttmnt = sttmnt.concat(", ?");
			}
			sttmnt = sttmnt.concat(");");						// finish defining insert statement.
			PreparedStatement ps = conn.prepareStatement(sttmnt);
			
			for(int i = 0; i < fldCount; i++){
				if((fields.get(i).equals("quantity")) && (!compType.equals("other"))){
					ps.setInt(i+1, Integer.parseInt(values.get(i)));		// only quantity field stored as an integer (unless in the "other" table)
				}else{
					ps.setString(i+1, values.get(i));
				}
			}
			ps.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("Error parsing new entry.");
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Gets list of tables in the database that the user is able to search and modify.
	 * @return List of table names in string format.
	 */
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

	/**
	 * Creates delete query for given row IDs in the given table.
	 * @param table The table to delete from.
	 * @param ids The IDs of the rows to be deleted.
	 * @return boolean representing success or failure of the delete operations.
	 */
	public boolean deleteRows(String table, List<String> ids)
	{
		for(String each : ids){
			String query = "delete from "+ table +" where id = "+ each;
			try {
				Statement stmnt = conn.createStatement();
				stmnt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
}
