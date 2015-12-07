package openWEI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.ArrayList;

/**
 * JPanel extension that contains search bar, component type selector, and JTable which
 * displays all given search results.
 * @author ShojiStudios
 * 
 */
public class Inventory_Pane extends JPanel{

	private static final long serialVersionUID = -5690829863264167768L;
	private List<String> colNames;
	private Client_Frame mainFrame;
	private NewData_Pane newDatPane;
	private static boolean admin;
	private List<String> typeNames;
	private List<int[]> modified;
	private String currentTable;
	
	private JButton searchButton;
	private JTextField keySearch;
	private JPanel searchInterface;
	private JTable searchTable;
	private JScrollPane resultScroll;
	private JComboBox<Object[]> types;
	private JLabel searchLabel;
	private JLabel inLabel;
	private DefaultTableModel tableModel;
	TableHandler tableChanges;
	private ResultSet currentResults;
	
	/**
	 * constructor for JPanel that shows all search information.
	 * @param clientIn
	 */
	public Inventory_Pane(Client_Frame clientIn)
	{
		mainFrame = clientIn;
		typeNames = new ArrayList<String>();
		colNames = null;
		admin = false;
		modified = new ArrayList<int[]>();
		currentTable = "";
		
		searchButton = new JButton("Search");
		keySearch = new JTextField("", 40);
		types = new JComboBox<Object[]>();
		//types.setModel(new DefaultComboBoxModel(typeNames.toArray()));
		searchLabel = new JLabel("Key Words:");
		inLabel = new JLabel("in");
		searchInterface = new JPanel(new FlowLayout());
		
		this.setLayout(new BorderLayout());
		
		types.getSelectedIndex();
		searchTable = new JTable();
		resultScroll = new JScrollPane(searchTable);
		
		searchTable.setAutoCreateRowSorter(true);
		
		add(searchInterface, BorderLayout.NORTH);
		searchInterface.add(searchLabel);
		searchInterface.add(keySearch);
		searchInterface.add(inLabel);
		searchInterface.add(types);
		searchInterface.add(searchButton);
		add(resultScroll, BorderLayout.CENTER);
		
		inventoryHandler myHandl = new inventoryHandler();
		keySearch.addActionListener(myHandl);
		searchButton.addActionListener(myHandl);
		
		tableChanges = new TableHandler();
		
	}
	
	/**
	 * Used for cell edits on JTable of search results. Stores each edited cell in a list.
	 * @author ShojiStudios
	 *
	 */
	private class TableHandler implements TableModelListener
	{
		public void tableChanged(TableModelEvent event) {
			int row = searchTable.getEditingRow();
			int col = searchTable.getEditingColumn(); 
			row = searchTable.convertRowIndexToModel(row);
			col = searchTable.convertColumnIndexToModel(col);
			int[] tmp = {row, col};
			modified.add(tmp);
			System.out.println(String.format("row: %d\tcol: %d", row, col));
		}
	}
	
	/**
	 * Confirms if user wants to commit all changes made:
	 * Yes commits all changes and returns list of changes.
	 * No undoes all changes and returns null.
	 * Cancel takes no action and returns null.
	 * @return List of List of Strings, where each list holds table name, column name, modified value, and the row ID.
	 */
	public List<List<String>> confirmModify(){
		String listMod = "";
		for(int[] each : modified){
			listMod = listMod.concat("\n" + searchTable.getValueAt(
					searchTable.convertRowIndexToView(each[0]), searchTable.convertColumnIndexToView(1)));
		}
		if(listMod.length() < 1){ return null; }
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to modify the following rows?:" + listMod, "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION);
		if(confirm == JOptionPane.YES_OPTION){
			List<List<String>> modifications = new ArrayList<List<String>>();
			for(int[] each : modified){
				modifications.add(convertRowToString(each[0], each[1]-1));
			}
			
			return modifications;
		}else if(confirm == JOptionPane.NO_OPTION){
			modified.clear();
			return null;
		}else{
			return null;
		}
		
	}
	
	/**
	 * Builds List of Strings to be used in SQL update.
	 * @param row is the row that was modified
	 * @param col is the column that was modified for that row
	 * @return List of Strings holding table name, column name, modified value, and the row ID.
	 */
	private List<String> convertRowToString(int row, int col){
		List<String> toModify = new ArrayList<String>();
		String colName = searchTable.getColumnName(col);
		String value = searchTable.getValueAt(row, col).toString();
		String id = tableModel.getValueAt(row, 0).toString();
		toModify.add(currentTable);
		toModify.add(colName);
		toModify.add(value);
		toModify.add(id);
		return toModify;
	}
		
	/**
	 * Event handler for search button and pressing enter key in the keyword field.
	 * @author ShojiStudios
	 *
	 */
	private class inventoryHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			remove(resultScroll);
			currentTable = types.getSelectedItem().toString();
			String[] keywords = {currentTable, keySearch.getText()};
			currentResults = mainFrame.search(keywords);
			
			try {
				tableModel = buildTableModel(currentResults);
				tableModel.addTableModelListener(tableChanges);
				searchTable = new JTable(tableModel);
				searchTable.removeColumn(searchTable.getColumnModel().getColumn(0));
				resultScroll = new JScrollPane(searchTable);
				searchTable.setAutoCreateRowSorter(true);
				add(resultScroll, BorderLayout.CENTER);
			} catch (SQLException e) {
				System.out.println("error at building new table.");
				e.printStackTrace();
			}
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Builds a table model from the SQL ResultSet. Specified for admin privileges on what can be updated.
	 * @param rs the result set which is going to be displayed
	 * @return DefaultTableModel to build the search results table with.
	 * @throws SQLException when errors encountered in building the table.
	 */
	public static DefaultTableModel buildTableModel(ResultSet rs)
		throws SQLException{
		
		ResultSetMetaData metaData = rs.getMetaData();
		
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for(int col = 1; col <= columnCount; col++){
			columnNames.add(metaData.getColumnName(col));
		}
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while(rs.next())
		{
			Vector<Object> tmpVect = new Vector<Object>();
			for( int columnIndex = 1 ; columnIndex <= columnCount; columnIndex++)
			{
				tmpVect.add(rs.getObject(columnIndex));
			}
			data.add(tmpVect);
		}
		DefaultTableModel tmpModel = new DefaultTableModel(data, columnNames){
			@Override
			public boolean isCellEditable(int row, int col){
				if(admin){
					if(columnNames.get(col).equals("last_modified")){
						return false;					//admin cannot manually set "last modified". is controlled by DB
					}else{
						return true;					//admin can set all else
					}
				}else{
					if(columnNames.get(col).equals("quantity")){
						return true;					//user can only modify quantity.
					}else{
						return false;
					}
				}
			}
		};
		
		return tmpModel;
	}
	

	/**
	 * Called for class to set/update the list of tables that the user can search.
	 */
	public void setTableNames()
	{
		typeNames = mainFrame.getTables();
		types.setModel(new DefaultComboBoxModel(typeNames.toArray()));
		
		revalidate();
		repaint();
	}
	

	/**
	 * Clears the list of table cells that have been modified.
	 */
	public void resetModList(){
		modified.clear();
	}
	

	/**
	 * Sets current users admin status to the passed boolean value.
	 * @param status whether user has admin privileges or not.
	 */
	public void setAdmin(boolean status){
		admin = status;
	}
	

	/**
	 * returns whether or not current user has logged in as admin or not.
	 * @return boolean representing admin status.
	 */
	public boolean checkAdmin(){
		return admin;
	}
	

	/**
	 * Spawns a new window for user to enter values to input into the currently searched table.
	 * As such, user must have searched for a table before clicking the New Data button.
	 */
	public void createNew(){
		colNames = new ArrayList<String>();
		
		for(int i = 0; i < tableModel.getColumnCount(); i++){
			if((tableModel.getColumnName(i).equals("id")) || (tableModel.getColumnName(i).equals("last_modified"))){ 
				continue; 
			}
			colNames.add(tableModel.getColumnName(i));
		}
		
		newDatPane = new NewData_Pane(this, colNames);
		newDatPane.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newDatPane.setVisible(true);
		this.setEnabled(false);
		mainFrame.setBottomButtonEnabled(false);
	}
	
	/**
	 * For sending new data entry to database
	 * @param values user given values for each field.
	 */
	public void sendNew(List<String> values){
		boolean newResult = mainFrame.newEntry(currentTable, colNames, values);
		newDatPane.setEnabled(false);
		while(!newResult){
			int control = JOptionPane.showConfirmDialog(null, "Error adding new data: Try Again?", "New Data Error", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
			if(control == JOptionPane.YES_OPTION){
				newResult = mainFrame.newEntry(currentTable, colNames, values);
			}else if(control == JOptionPane.CANCEL_OPTION){
				newDatPane.setEnabled(true);
				return;
			}else{
				newResult = true;
				newDatPane.setVisible(false);
				newDatPane.dispose();				
			}
			
		}
		this.setEnabled(true);
		mainFrame.setBottomButtonEnabled(true);
		newDatPane.setVisible(false);
		newDatPane.dispose();
		keySearch.setText("");
		searchButton.doClick();
	}
	
	/**
	 * Cancels the addition of new data to table. Clears out NewData pane and re-enables main frame.
	 */
	public void cancelNew(){
		this.setEnabled(true);
		mainFrame.setBottomButtonEnabled(true);
		newDatPane.setVisible(false);
		newDatPane.dispose();
	}
	

	/**
	 *  Simple method for passing which rows in the JTable the user has selected.
	 *  Used when an admin has clicked a button in client frame for "delete".
	 * @return	An integer array containing the row numbers which are highlighted.
	 */ 
	public List<String> getSelectedRows()
	{
		int[] selected = searchTable.getSelectedRows();
		List<String> selectedIDs = new ArrayList<String>();
		for(int each : selected){
			selectedIDs.add(tableModel.getValueAt(each, 0).toString());
		}
		return selectedIDs;
	}
	
	/**
	 * Only used by delete selected rows button to get the current table name.
	 * @return String containing the currently searched tables name.
	 */
	public String getTable()
	{
		return currentTable;
	}

	/**
	 * Used to refresh the current table after a delete operation.
	 */
	public void refreshSearch(){
		searchButton.doClick();
	}
}