package openWEI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class NewData_Pane extends JFrame{
	
	private JButton submitBtn;
	private JButton cancel;
	private List<JLabel> labels;
	private List<JTextField> textFields;
	
	private Inventory_Pane inventory;
	
	
	public NewData_Pane(Inventory_Pane inventReference, List<String> fields)
	{
		inventory = inventReference;
		submitBtn = new JButton("Submit");
		cancel = new JButton("Cancel");
		
		for(String each : fields){
			labels.add(new JLabel(each));
			textFields.add(new JTextField());
		}
		
		this.setLayout(new GridLayout(labels.size(), 2, 5, 10));
		for(int i = 0; i < labels.size(); i++){
			add(labels.get(i));
			add(textFields.get(i));
			
		}
		
		add(submitBtn);
		add(cancel);
		ModEvents listener = new ModEvents();
		submitBtn.addActionListener(listener);
		cancel.addActionListener(listener);
	}
	
	
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
