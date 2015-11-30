package openWEI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.*;

/// JPanel extension that contains search bar, component type selector, and JTable which
/// displays all given search results.
public class Inventory_Pane extends JPanel{

	private static final long serialVersionUID = -5690829863264167768L;
	private JButton searchButton;
	private JTextField keySearch;
	private JPanel searchInterface;
	private JTable searchResults;
	private JScrollPane resultScroll;
	private String[] colNames = {"Name", "Notes", "Quantity", "Location", "Last_Modified", "Spec Sheets"};		// To be removed
	private String[] typeNames = {"Amplifier", "Battery", "Capacitor", "Fuse", 
			"Inductor", "LED", "Microcontroller", "Motor", "Resistor", "Sensor", "Transformer", "Transistor"}; 	// To be removed
	private JComboBox<String> types;
	private JLabel searchLabel;
	private JLabel inLabel;
	
	
	/// constructor for JPanel that shows all search information.
	public Inventory_Pane()
	{
		
		searchButton = new JButton("Search");
		keySearch = new JTextField("", 40);
		types = new JComboBox<String>(typeNames);		// CHANGE THIS TO A GETTER
		searchLabel = new JLabel("Key Words:");
		inLabel = new JLabel("in");
		searchInterface = new JPanel(new FlowLayout());
		
		this.setLayout(new BorderLayout());
		
		TableModel myModel = new AbstractTableModel(){
			private static final long serialVersionUID = 356177746501450517L;
			public int getColumnCount() { return 6; }										// NEED TO UPDATE THIS (obviously)
			public int getRowCount() { return 500; }										// NEED TO UPDATE THIS (obviously)
			public String getColumnName(int col) { return  colNames[col]; }					// NEED TO UPDATE THIS (obviously)
			public Object getValueAt(int row, int col) { return  tableFiller(row, col); } 	// NEED TO UPDATE THIS (obviously)
		};
		
		types.getSelectedIndex();
		searchResults = new JTable(myModel);
		resultScroll = new JScrollPane(searchResults);
		
		searchResults.setAutoCreateRowSorter(true);
		
		add(searchInterface, BorderLayout.NORTH);
		searchInterface.add(searchLabel);
		searchInterface.add(keySearch);
		searchInterface.add(inLabel);
		searchInterface.add(types);
		searchInterface.add(searchButton);
		add(resultScroll, BorderLayout.CENTER);
		
		inventHandler myHandl = new inventHandler();
		
		keySearch.addActionListener(myHandl);
		searchButton.addActionListener(myHandl);
		
		
	}
	
	/// simple method for passing which rows in the JTable the user has selected.
	/// generally used when an admin has clicked a button in client frame for "update" or "delete".
	/// output: an integer array containing the row numbers which are highlighted.
	public int[] getSelectedRows()
	{
		int[] selected = searchResults.getSelectedRows();
		for(int each : selected) System.out.print(String.format("%03d", each));
		return selected;
	}
	
	/// Called by JTable to request data for each row and column.
	/// output: String containing data for requested cell.
	private String tableFiller(int row, int col)
	{
		String nodeVal = "testData";
		
		
		return nodeVal;
	}
	
	/// event handler for search button and pressing enter key in the keyword field.
	private class inventHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			
		}
	}
	
	/// A simple method for passing user input for search requests.
	/// output: String containing users keywords to search, and the component type selected.
	public String search()
	{
		String keywords = keySearch.getText() + ";" +types.getSelectedItem().toString();
		
		return keywords;
	}
	
}
