package GUI;

import java.awt.GridLayout;
import java.util.List;

import javax.sound.sampled.LineListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import LOGIC.Key;
import LOGIC.User;
import javafx.util.Pair;

public class ExportKey extends JPanel {
	private User user;
	
	private JPanel tablePanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private JTable dataTable;
	private JButton export = new JButton("Export key");
	private JButton delete = new JButton("Delete key");
	
	public ExportKey(User user) {
		this.user = user;
		setLayout(new GridLayout(2, 0));	
		
		loadTable();
		add(dataTable);
		
		buttonPanel.add(export);
		buttonPanel.add(delete);
		add(buttonPanel);
		
		delete.addActionListener(l -> {
			if (dataTable.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this,"Please select the key in table");
				return;
			}
			
			Long id = Long.parseLong((String) dataTable.getValueAt(dataTable.getSelectedRow(),0));
			
			if ((String) dataTable.getValueAt(dataTable.getSelectedRow(),3) == "true")
			{
				String password = JOptionPane.showInputDialog("Insert password");
				if (password == null || password.length() == 0)
				{
					JOptionPane.showMessageDialog(this,"Please enter password");
					return;
				}

				System.out.println(Key.deleteKeyPassword(id, password));
				return;
			}
			Key.deleteKey(id);
		});
		
		export.addActionListener(l -> {
			if (dataTable.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this,"Please select the key in table");
				return;
			}
			
			Long id = Long.parseLong((String) dataTable.getValueAt(dataTable.getSelectedRow(),0));
			
			String publicFlag = (String) dataTable.getValueAt(dataTable.getSelectedRow(),2);
			String privateFlag = (String) dataTable.getValueAt(dataTable.getSelectedRow(),3);
			int n = -1;
			if (publicFlag == "true" && privateFlag == "true") {
				Object[] colours = {"Public", "Secret"};

				n = JOptionPane.showOptionDialog(null,
				    "Which key?",
				    "Choose a key",
				    JOptionPane.DEFAULT_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    colours,
				    colours[0]);
				System.out.println(n);
			}
			
			if ((publicFlag == "true" && n==-1) || n==0) {
				if(Key.exportPublicKey(id)) {
					JOptionPane.showMessageDialog(this,"Public key has been exported");
				}
				else {
					JOptionPane.showMessageDialog(this,"There was an error");
				}
			}
			else {
				if(Key.exportSecretKey(id)) {
					JOptionPane.showMessageDialog(this,"Secret key has been exported");
				}
				else {
					JOptionPane.showMessageDialog(this,"There was an error");
				}
			}
		});
	}
	
	public void loadTable() {
		String[] columns = {"Key Id", "User info", "Public", "Secret"};
		String data[][] = new String[Key.numberOfKeys()][4];
		int i=0;
		for(Pair<Long, String> p: Key.getAllKeyRing()) {
			data[i][0] = p.getKey().toString();
			char c = p.getValue().charAt(p.getValue().length()-1);
			data[i][1] = p.getValue().substring(0, p.getValue().length()-1);
			
			if (c == '1' || c == '3')
				data[i][2] = "true";
			if (c == '2' || c == '3')
				data[i][3] = "true";
			
			i++;
		}
		dataTable = new JTable(data, columns);
	}
}
