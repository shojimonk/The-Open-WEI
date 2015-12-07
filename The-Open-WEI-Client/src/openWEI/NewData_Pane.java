package openWEI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Simple dynamically built frame to display given "field" labels, and 
 * a text box for the user to give each field a value.
 * @author ShojiStudios
 *
 */
public class NewData_Pane extends JFrame{
	
	private static final long serialVersionUID = -3240291117230158903L;
	private Inventory_Pane inventory;
	
	private JButton submitBtn;
	private JButton cancel;
	private List<JLabel> labels;
	private List<JTextField> textFields;
	
	
	/**
	 * Constructor. Builds layout based on input 
	 * @param inventReference A reference to the caller class for passing back values.
	 * @param fields Table-supplied fields for user to give values to.
	 */
	public NewData_Pane(Inventory_Pane inventReference, List<String> fields)
	{
		super("Enter New Data");
		inventory = inventReference;
		submitBtn = new JButton("Submit");
		cancel = new JButton("Cancel");
		labels = new ArrayList<JLabel>();
		textFields = new ArrayList<JTextField>();
		for(String each : fields){
			labels.add(new JLabel(each));
			textFields.add(new JTextField(""));
		}
		
		this.setLayout(new GridLayout(labels.size()+1, 2, 5, 10));
		for(int i = 0; i < labels.size(); i++){
			add(labels.get(i));
			add(textFields.get(i));
		}
		
		add(submitBtn);
		add(cancel);
		ModEvents listener = new ModEvents();
		submitBtn.addActionListener(listener);
		cancel.addActionListener(listener);
		this.pack();
		int width = this.getWidth();
		int height = this.getHeight();
		this.setSize(width+50, height+20);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Simple listener to detect user presses of Submit or Cancel buttons.
	 * @author ShojiStudios
	 *
	 */
	public class ModEvents implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			if(event.getSource() == cancel){
				inventory.cancelNew();
			}else{
				List<String> values = new ArrayList<String>();
				for(JTextField each : textFields){
					values.add(each.getText());
				}
				inventory.sendNew(values);
			}
		}	
	}
}
