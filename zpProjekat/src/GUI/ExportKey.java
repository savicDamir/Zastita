package GUI;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import LOGIC.User;

public class ExportKey extends JPanel {
	private User user;
	
	private JPanel tablePanel = new JPanel();
	private JPanel exportPanel = new JPanel();
	
	private JTable dataTable;
	private JButton export = new JButton("Export key");
	
	public ExportKey(User user) {
		this.user = user;
		
		dataTable= new JTable(5,4);
		tablePanel.add(dataTable);
		add(tablePanel);
		exportPanel.add(export);
		add(exportPanel);
	}
}
