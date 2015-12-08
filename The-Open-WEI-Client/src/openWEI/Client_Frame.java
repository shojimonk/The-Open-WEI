package openWEI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import java.util.List;
import java.awt.event.*;
import java.sql.*;

/**
 * The main frame for this program. Controls program logic and user interactions.
 * Instantiates and uses Inventory_Pane, Login_Pane, and Database_Communications. 
 * @author ShojiStudios
 *
 */
public class Client_Frame extends JFrame{

	private static final long serialVersionUID = 6441095492601536502L;
	private Inventory_Pane iPane;
	private Login_Pane lPane;
	private Database_Communications comms;
	private Client_Frame selfReference;
	
	private JButton createNew;
	private JButton commitModify;
	private JButton deleteSelected;
	//private JButton importCSV;		//not implemented
	private JButton userLogin;
	private JButton userLogout;
	private JPanel bottomButtons;

	/**
	 * Instantiates all panels and buttons, as well as custom classes. Sets handlers for all objects.
	 */
	public Client_Frame()
	{
		super("The Open WEI");
		this.setLayout(new BorderLayout());
		iPane = new Inventory_Pane(this);
		comms = new Database_Communications();
		selfReference = this;		// used for instantiating lPane from event handler.
		
		createNew = new JButton("Create New Entry");
		commitModify = new JButton("Commit Modifications");
		deleteSelected = new JButton("Delete Selected Data");
		//importCSV = new JButton("Import .csv");			//not implemented
		userLogin = new JButton("Log In");
		userLogout = new JButton("Log Out");
		
		bottomButtons = new JPanel(new FlowLayout());
		
		add(iPane, BorderLayout.CENTER);
		
		add(bottomButtons, BorderLayout.SOUTH);
		
		bottomButtons.add(commitModify);
		bottomButtons.add(userLogin);
		
		Client_Handler myHandler = new Client_Handler();
		userLogin.addActionListener(myHandler);
		userLogout.addActionListener(myHandler);
		commitModify.addActionListener(myHandler);
		deleteSelected.addActionListener(myHandler);
		createNew.addActionListener(myHandler);
		//importCSV.addActionListener(myHandler);			//not implemented
		
		createNew.setEnabled(false);
		deleteSelected.setEnabled(false);
				
	}
	
	/**
	 * Main control flow handled through user interaction with buttons, and limiting which 
	 * buttons/panels are accessible to the user.
	 * @author ShojiStudios
	 *
	 */
	private class Client_Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == userLogin)
			{
				remove(iPane);
				lPane = new Login_Pane(selfReference);
				add(lPane, BorderLayout.CENTER);
				userLogin.setEnabled(false);
				commitModify.setEnabled(false);
				//lPane.requestFocusInWindow();
				revalidate();
				repaint();
				lPane.setVisible(true);
			}
			else if(event.getSource() == userLogout)
			{
				removeAdmin();
			}
			else if(event.getSource() == commitModify)
			{
				List<List<String>> modifications = iPane.confirmModify();
				if(modifications != null){
					boolean updated = comms.modifyData(modifications);
					iPane.resetModList();
					System.out.println("updated?:" + updated);
					return;
				}
			}
			else if(event.getSource() == createNew)
			{
				iPane.createNew();
			}
			else if(event.getSource() == deleteSelected){
				comms.deleteRows(iPane.getTable(), iPane.getSelectedRows());
				iPane.refreshSearch();
			}
		}
	}
	
	/**
	 * Checks for database at given host and port. 
	 * @param hostPort The host:port to check, in String format.
	 * @return True if successfully connected to DB, else false.
	 */
	public boolean dbCheck(String hostPort)
	{
		try {
			Boolean isHost = comms.checkForHost(hostPort);
			if(isHost) { iPane.setTableNames(); }			// if there is a host at the port, set the list of component types 
			return isHost;
		} catch (SQLException ex) {
			System.out.println("failed in client frame...");
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Passes user given login info to database communications.
	 * @param logInfo User entered username and password to check for.
	 * @return True if login name/pass match. else false.
	 */
	public boolean login(String[] logInfo)
	{
		return comms.checkLogin(logInfo);
	}
	
	/**
	 * Returns user to Inventory Pane without attempting to log in.
	 */
	public void cancelLogin()
	{
		remove(lPane);
		add(iPane, BorderLayout.CENTER);
		userLogin.setEnabled(true);
		commitModify.setEnabled(true);
		revalidate();
		repaint();
	}
	
	/**
	 * Called when user has successfully logged in. Adds admin functionality buttons.
	 */
	public void grantAdmin()
	{
		bottomButtons.remove(userLogin);
		bottomButtons.add(createNew);
		bottomButtons.add(deleteSelected);
		//bottomButtons.add(importCSV);			//not implemented
		bottomButtons.add(userLogout);
		commitModify.setEnabled(true);
		
		remove(lPane);
		
		add(iPane, BorderLayout.CENTER);
		iPane.setAdmin(true);
		revalidate();
		repaint();
	}
	
	/**
	 * Called when a user logs out. Removes admin functionality buttons.
	 */
	public void removeAdmin()
	{
		bottomButtons.remove(userLogout);
		bottomButtons.add(userLogin);
		bottomButtons.remove(createNew);
		bottomButtons.remove(deleteSelected);
		//bottomButtons.remove(importCSV);		//not implemented
		iPane.setAdmin(false);
		revalidate();
		repaint();
	}
	
	/**
	 * A simple method for passing user input for search requests.
	 * Enables create new and delete selected buttons, as they have no functionality until 
	 * the user has performed a search on a table.
	 * @param searchString String containing users keywords to search, and the component type selected.
	 * @return ResultSet containing results of the users search.
	 */
	public ResultSet search(String[] searchString)		// changed from returning ArrayList<ArrayList<String>>
	{
		createNew.setEnabled(true);
		deleteSelected.setEnabled(true);
		return comms.sendQuery(searchString);
	}

	/**
	 * Gets list of searchable tables in the Database
	 * @return List of names of searchable tables in the connected database.
	 */
	public List<String> getTables()
	{
		return comms.getTables();
	}

	/**
	 * Used to batch enable/disable user features when only one area should be interacted with.
	 * @param truth Whether to enable (true) or disable (false) the buttons.
	 */
	public void setBottomButtonEnabled(boolean truth){
		userLogin.setEnabled(truth);
		userLogout.setEnabled(truth);
		commitModify.setEnabled(truth);
		deleteSelected.setEnabled(truth);
		createNew.setEnabled(truth);
		//importCSV.setEnabled(truth);		//not implemented
		bottomButtons.setEnabled(truth);
	}
	
	/**
	 * Passes new data entry request to Database Communications class.
	 * @param table Name of the Table being added to.
	 * @param columns List of columns within the table.
	 * @param newData List of user entered data to enter into each column
	 * @return Boolean representing success or failure of addition of new entry.
	 */
	public boolean newEntry(String table, List<String> columns, List<String> newData)
	{
		return comms.newEntry(table, columns, newData);
	}
}
