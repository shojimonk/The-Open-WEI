package openWEI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GUI_Runtime {
	
	/// Main method run at startup. checks if arguments for host and port were passed. 
	/// Asks for them if not given or if given values fail.
	public static void main(String[] args) {
		
		
		Client_Frame myFrame = new Client_Frame();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(1024, 768);
		
		Boolean isHost = false; 
		
		while(!isHost)
		{
			String hostPort = "";
			if(args.length != 2)
			{
				String host = JOptionPane.showInputDialog(null, "Enter Host", "TITLE", JOptionPane.QUESTION_MESSAGE);
				String port = JOptionPane.showInputDialog(null, "Enter Port", "TITLE_2", JOptionPane.QUESTION_MESSAGE);
				hostPort = hostPort.concat(host);
				if(port.length() > 0){
					hostPort = hostPort.concat(":");
					hostPort = hostPort.concat(port);					
				}
			}
			else
			{
				hostPort = hostPort.concat(args[0]);
				hostPort = hostPort.concat(args[1]);
			}
			
			isHost = myFrame.dbCheck(hostPort);			
			if(!isHost)
			{
				JOptionPane.showMessageDialog(null, "Could not Contact DB at: " + hostPort, "Title_3", JOptionPane.WARNING_MESSAGE);
				args = null;
			}
		}
		myFrame.setVisible(true);
	}
}
