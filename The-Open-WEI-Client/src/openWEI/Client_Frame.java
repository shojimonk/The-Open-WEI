package openWEI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;

import java.awt.event.*;

/// The main frame for this program. Controls program logic and user interactions.
/// Instantiates and uses Inventory_Pane, Login_Pane, and Database_Communications. 
public class Client_Frame extends JFrame{

	private static final long serialVersionUID = 6441095492601536502L;
	private Inventory_Pane sPane;
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
	public Client_Frame(String hostPort)
	{
		super("The Open WEI");
		this.setLayout(new BorderLayout());
		sPane = new Inventory_Pane();
		lPane = new Login_Pane();
		comms = new Database_Communications();
		
		createNew = new JButton("Create New Entry");
		modifySelected = new JButton("Modify Selected Data");
		deleteSelected = new JButton("Delete Selected Data");
		importCSV = new JButton("Import .csv");
		userLogin = new JButton("Log In");
		userLogout = new JButton("Log Out");
		
		bottomButtons = new JPanel(new FlowLayout());
		
		add(sPane, BorderLayout.CENTER);
		
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
		
		loginHandler loginButton = new loginHandler();
		lPane.addComponentListener(loginButton);
		
	}
	
	/// Main control flow handled through user interaction with buttons, and limiting which 
	/// buttons/panels are accessible to the user.
	private class actionHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == userLogin)
			{
				
			}
			else if(event.getSource() == userLogout)
			{
				
			}
			else if(event.getSource() == modifySelected)
			{
				
			}
			else if(event.getSource() == createNew)
			{
				
			}
			
		}
	}
	
	/// Handles events triggered by changes to JPanels. Used by Login_Pane for 
	/// triggering Client_Frame to grab username/pass and call login method.  
	private class loginHandler implements ComponentListener
	{
		public void componentHidden(ComponentEvent arg0) {
			
			//Logic here for calling login() method.
			String loginInfo = lPane.getNameAndPass();
			Boolean logResult = comms.checkLogin(loginInfo);
			if(logResult)
			{
				bottomButtons.remove(userLogin);
				bottomButtons.add(createNew);
				bottomButtons.add(deleteSelected);
				bottomButtons.add(importCSV);
				bottomButtons.add(userLogout);
				
				remove(lPane);
				add(sPane, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
			
		}

		public void componentMoved(ComponentEvent arg0) {
			
		}

		public void componentResized(ComponentEvent arg0) {
			
		}

		public void componentShown(ComponentEvent arg0) {
			
		}
	}
	
	public Boolean dbCheck(String hostPort)
	{
		return comms.checkForHost(hostPort);
	}

}
