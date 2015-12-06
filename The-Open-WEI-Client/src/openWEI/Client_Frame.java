package openWEI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;

import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;
import java.sql.*;

/// The main frame for this program. Controls program logic and user interactions.
/// Instantiates and uses Inventory_Pane, Login_Pane, and Database_Communications. 
public class Client_Frame extends JFrame{

	private static final long serialVersionUID = 6441095492601536502L;
	private Inventory_Pane iPane;
	private Login_Pane lPane;
	private Database_Communications comms;
	private Client_Frame selfReference;
	
	private JButton createNew;
	private JButton commitModify;
	private JButton deleteSelected;
	private JButton importCSV;
	private JButton userLogin;
	private JButton userLogout;
	private JPanel bottomButtons;

	/// Instantiates all panels and buttons, as well as custom classes.
	/// Sets handlers for all objects.
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
		importCSV = new JButton("Import .csv");
		userLogin = new JButton("Log In");
		userLogout = new JButton("Log Out");
		
		bottomButtons = new JPanel(new FlowLayout());
		
		add(iPane, BorderLayout.CENTER);
		
		add(bottomButtons, BorderLayout.SOUTH);
		
		bottomButtons.add(commitModify);
		bottomButtons.add(userLogin);
		
		actionHandler myHandler = new actionHandler();
		userLogin.addActionListener(myHandler);
		userLogout.addActionListener(myHandler);
		commitModify.addActionListener(myHandler);
		deleteSelected.addActionListener(myHandler);
		createNew.addActionListener(myHandler);
		importCSV.addActionListener(myHandler);
				
	}
	
	/// Main control flow handled through user interaction with buttons, and limiting which 
	/// buttons/panels are accessible to the user.
	private class actionHandler implements ActionListener
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
					System.out.println("Not null.");
					boolean updated = comms.modifyData(modifications);
					System.out.println("updated?:" + updated);
					return;
				}
				System.out.println("Is null.");
				//iPane.getSelectedRows();
				//iPane.createMod();
			}
			else if(event.getSource() == createNew)
			{
				// spawn window for entering new data 
			}
			
		}
	}
	
	/*
	 * Checks for database at given host and port. 
	 * Output: True if successfully connected to DB, else false.
	 */
	public Boolean dbCheck(String hostPort)
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
	
	/*
	 * Passes user given login info to database communications.
	 * Output: true if login name/pass match. else false.
	 */
	public Boolean login(String[] logInfo)
	{
		return comms.checkLogin(logInfo);
	}
	
	public void cancelLogin()
	{
		remove(lPane);
		add(iPane, BorderLayout.CENTER);
		userLogin.setEnabled(true);
		commitModify.setEnabled(true);
		revalidate();
		repaint();
	}
	
	public void grantAdmin()
	{
		bottomButtons.remove(userLogin);
		bottomButtons.add(createNew);
		bottomButtons.add(deleteSelected);
		bottomButtons.add(importCSV);
		bottomButtons.add(userLogout);
		commitModify.setEnabled(true);
		
		remove(lPane);
		
		add(iPane, BorderLayout.CENTER);
		iPane.setAdmin(true);
		revalidate();
		repaint();
	}
	
	public void removeAdmin()
	{
		bottomButtons.remove(userLogout);
		bottomButtons.add(userLogin);
		bottomButtons.remove(createNew);
		bottomButtons.remove(deleteSelected);
		bottomButtons.remove(importCSV);
		iPane.setAdmin(false);
		revalidate();
		repaint();
	}
	
	/*
	 *  A simple method for passing user input for search requests.
	 *  output: String containing users keywords to search, and the component type selected.
	 */
	
	public ResultSet search(String[] searchString)		// changed from returning ArrayList<ArrayList<String>>
	{
		return comms.sendQuery(searchString);
	}

	public List<String> getTables()
	{
		return comms.getTables();
	}
	
}
