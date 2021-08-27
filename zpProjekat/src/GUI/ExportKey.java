package GUI;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class ExportKey extends JPanel {

	private JPanel tablePanel = new JPanel();
	private JPanel exportPanel = new JPanel();
	
	private JTable dataTable;
	private JButton export = new JButton("Export key");
	
	public ExportKey() {
		dataTable= new JTable(5,4);
		tablePanel.add(dataTable);
		add(tablePanel);
		exportPanel.add(export);
		add(exportPanel);
	}
}
