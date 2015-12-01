package openWEI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;

import java.awt.event.*;
import java.sql.*;

/// The main frame for this program. Controls program logic and user interactions.
/// Instantiates and uses Inventory_Pane, Login_Pane, and Database_Communications. 
public class Client_Frame extends JFrame{

	private static final long serialVersionUID = 6441095492601536502L;
	private Inventory_Pane iPane;
	private Login_Pane lPane;
	private Database_Communications comms;
	
	private JButton createNew;
	private JButton modifySelected;
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
		lPane = new Login_Pane(this);
		comms = new Database_Communications();
		
		createNew = new JButton("Create New Entry");
		modifySelected = new JButton("Modify Selected Data");
		deleteSelected = new JButton("Delete Selected Data");
		importCSV = new JButton("Import .csv");
		userLogin = new JButton("Log In");
		userLogout = new JButton("Log Out");
		
		bottomButtons = new JPanel(new FlowLayout());
		
		add(iPane, BorderLayout.CENTER);
		
		add(bottomButtons, BorderLayout.SOUTH);
		
		bottomButtons.add(modifySelected);
		bottomButtons.add(userLogin);
		
		actionHandler myHandler = new actionHandler();
		userLogin.addActionListener(myHandler);
		userLogout.addActionListener(myHandler);
		modifySelected.addActionListener(myHandler);
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
				add(lPane, BorderLayout.CENTER);
				revalidate();
				repaint();
				lPane.setVisible(true);
			}
			else if(event.getSource() == userLogout)
			{
				removeAdmin();
			}
			else if(event.getSource() == modifySelected)
			{
				iPane.getSelectedRows();
			}
			else if(event.getSource() == createNew)
			{
				// spawn window for entering new data 
			}
			
		}
	}
	
	public Boolean dbCheck(String hostPort)
	{
		try {
			return comms.checkForHost(hostPort);
		} catch (SQLException ex) {
			System.out.println("failed in client frame...");
			ex.printStackTrace();
		}
		return false;
	}
	
	public Boolean login(String logInfo)
	{
		return comms.checkLogin(logInfo);
	}
	
	public void grantAdmin()
	{
		bottomButtons.remove(userLogin);
		bottomButtons.add(createNew);
		bottomButtons.add(deleteSelected);
		bottomButtons.add(importCSV);
		bottomButtons.add(userLogout);
		
		remove(lPane);
		add(iPane, BorderLayout.CENTER);
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

		revalidate();
		repaint();
	}
	
	/// A simple method for passing user input for search requests.
	/// output: String containing users keywords to search, and the component type selected.
	public ResultSet search(String[] searchString)
	{
		ResultSet searchResults = comms.sendQuery(searchString);
		return searchResults;
	}

}
