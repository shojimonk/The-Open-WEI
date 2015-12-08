package openWEI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import jBCrypt.BCrypt;

/**
 * extension of JPanel for displaying Username and Password fields for user login.
 * @author ShojiStudios
 *
 */
public class Login_Pane extends JPanel{

	private static final long serialVersionUID = -4615172399730986636L;
	private Client_Frame mainFrame;
	
	private JButton logIn;
	private JButton cancel;
	private JTextField name;
	private JPasswordField pass;
	private JLabel nameLabel;
	private JLabel passLabel;
	
	/**
	 * Constructor. Only initializes all buttons, text/password fields, and handlers
	 * @param clientIn
	 */
	public Login_Pane(Client_Frame clientIn)
	{
		mainFrame = clientIn;
		logIn = new JButton("Submit");
		cancel = new JButton("Cancel");
		name = new JTextField("", 20);
		pass = new JPasswordField("", 20);
		nameLabel = new JLabel("Name:");
		passLabel = new JLabel("Pass:");
		
		this.setLayout(new FlowLayout());
		add(nameLabel);
		add(name);
		add(passLabel);
		add(pass);
		add(logIn);
		add(cancel);
		
		Login_Handler loginEvent = new Login_Handler();
		logIn.addActionListener(loginEvent);
		cancel.addActionListener(loginEvent);
		name.addActionListener(loginEvent);
		pass.addActionListener(loginEvent);
		logIn.requestFocusInWindow();
	}

	/**
	 * Method called by client frame to grab entered username and pass. 
	 * @return String containing username and encrypted password.
	 */
	public String[] getNameAndPass()
	{
		// **** Encryption for setting new passwords here. Currently do not have "add new users" as an implemented feature. ****
		//String passSalt = BCrypt.gensalt(10);
		//String hashedPass = BCrypt.hashpw(new String(pass.getPassword()), passSalt);
		//System.out.println("Hashed to: " + hashedPass);
		
		
		String tmpPass = new String(pass.getPassword());
		String tmpName= name.getText();
		String[] namePass = {tmpName, tmpPass};
		pass.setText("");
		return namePass;
	}
	
	/**
	 * Event handler for login pane. Passes user commands back to Client Frame.
	 * @author ShojiStudios
	 *
	 */
	private class Login_Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			if(event.getSource() == cancel){
				mainFrame.cancelLogin();
			}else{
				String[] logInfo = getNameAndPass();
				Boolean logResult = mainFrame.login(logInfo);
				if(logResult)
				{
					mainFrame.grantAdmin();
				}
			}
		}
	}
	
}
