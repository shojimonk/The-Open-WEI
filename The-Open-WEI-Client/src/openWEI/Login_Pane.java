package openWEI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import jBCrypt.BCrypt;

/// extension of JPanel for displaying username and password fields for user login.
/// will encrypt password data before passing off to Client_Frame.
public class Login_Pane extends JPanel{

	private static final long serialVersionUID = -4615172399730986636L;
	private JButton logIn;
	private JButton cancel;
	private JTextField name;
	private JPasswordField pass;
	private JLabel nameLabel;
	private JLabel passLabel;
	private Client_Frame mainFrame;
	
	/// Constructor. Only initializes all buttons, text/password fields, and handlers
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
		
		buttHandler loginEvent = new buttHandler();
		logIn.addActionListener(loginEvent);
		cancel.addActionListener(loginEvent);
		name.addActionListener(loginEvent);
		pass.addActionListener(loginEvent);
		//nameLabel.setEnabled(false);
		//passLabel.setEnabled(false);
		logIn.requestFocusInWindow();
	}

	/// method called by client frame to grab entered username and pass. 
	/// encryption occurs before passing off data.
	/// Output: String containing username and encrypted password.
	public String[] getNameAndPass()
	{
		// Put encryption here !
		//String passSalt = BCrypt.gensalt(10);
		//String hashedPass = BCrypt.hashpw(new String(pass.getPassword()), passSalt);
		//System.out.println("Hashed to: " + hashedPass);
		
		String tmpPass = new String(pass.getPassword());		//dont use this because strings hold data invisibly after being cleared
		String tmpName= name.getText();
		String[] namePass = {tmpName, tmpPass};
		pass.setText("");
		return namePass;
	}
	
	/// Event handler for login pane. Whenever button is pressed, adds JLabel to trigger component event in Client Frame.
	private class buttHandler implements ActionListener
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
