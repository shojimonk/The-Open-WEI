package openWEI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/// extension of JPanel for displaying username and password fields for user login.
/// will encrypt password data before passing off to Client_Frame.
public class Login_Pane extends JPanel{

	private static final long serialVersionUID = -4615172399730986636L;
	private JButton logIn;
	private JTextField name;
	private JPasswordField pass;
	private JLabel nameLabel;
	private JLabel passLabel;
	private JLabel addStatus;
	
	/// Constructor. Only initializes all buttons, text/password fields, and handlers
	public Login_Pane()
	{
		logIn = new JButton("Submit");
		name = new JTextField("", 20);
		pass = new JPasswordField("", 20);
		nameLabel = new JLabel("Name:");
		passLabel = new JLabel("Pass:");
		addStatus = new JLabel("Checking Login...");
		
		
		this.setLayout(new FlowLayout());
		add(nameLabel);
		add(name);
		add(passLabel);
		add(pass);
		add(logIn);
		
		buttHandler loginEvent = new buttHandler();
		logIn.addActionListener(loginEvent);
		name.addActionListener(loginEvent);
		pass.addActionListener(loginEvent);
	}

	/// method called by client frame to grab entered username and pass. 
	/// encryption occurs before passing off data.
	/// Output: String containing username and encrypted password.
	public String getNameAndPass()
	{
		// Put encryption here !
		
		String tmpPass = new String(pass.getPassword());
		String endresult = String.format("%s, %s", name.getText(), tmpPass);
		System.out.println(String.format("OUTPUT IS: %s", endresult));
		setVisible(false);
		return endresult;
	}
	
	/// Event handler for login pane. Whenever button is pressed, adds JLabel to trigger component event in Client Frame.
	private class buttHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			add(addStatus);
			revalidate();
			repaint();

		}
	}
	
}
