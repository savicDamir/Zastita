package GUI;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.bouncycastle.openpgp.PGPException;

import LOGIC.Key;
import LOGIC.PasswordError;
import LOGIC.Send;
import javafx.util.Pair;

public class SendMessage extends JPanel {

	JPanel panel[] = new JPanel[6];
	JLabel label = new JLabel("Message :");
	JTextField textField = new JTextField(25);
	
	JCheckBox sign = new JCheckBox("Sign");
	JCheckBox encrypt = new JCheckBox("Encrypt");
	JCheckBox zip = new JCheckBox("Zip");
	JCheckBox radix = new JCheckBox("Radix");
	
	JLabel publicDataLabel = new JLabel("Choose public key to sign message");
	JLabel privateDataLabel = new JLabel("Choose private key to sign message");
	
	JButton sendMessage = new JButton("Send message");
	
	private JTable publicDataTable;
	private JTable privateDataTable;
	public SendMessage() {
		setLayout(new GridLayout(7,1));
		
		panel[0] = new JPanel();
		panel[0].add(label);
		panel[0].add(textField);
		add(panel[0]);
	
		panel[1] = new JPanel();
		panel[1].add(sign);
		panel[1].add(encrypt);
		panel[1].add(zip);
		add(panel[1]);
		
		add(privateDataLabel);
		loadPrivateTable();
		
		add(publicDataLabel);
		loadPublicTable();
		
		add(sendMessage);
		sendMessage.addActionListener(l -> {
			boolean signFlag = sign.isSelected();
			boolean encryptFlag = encrypt.isSelected();
			boolean zipFlag = zip.isSelected();
			
			//Long idPublic = Long.parseLong((String) publicDataTable.getValueAt(publicDataTable.getSelectedRow(),0));
			
			if (textField.getText().length()<=0) {
				JOptionPane.showMessageDialog(this,"Please enter message");
				return;
			}
			String message = textField.getText();
			byte[] byteMessage = null;
			if (signFlag) {
				if ((String) privateDataTable.getValueAt(privateDataTable.getSelectedRow(),0)== "-1")
				{
					JOptionPane.showMessageDialog(this,"Please choose private key");
					return;
				}
				Long idPrivate = Long.parseLong((String) privateDataTable.getValueAt(privateDataTable.getSelectedRow(),0));
				
				String password = JOptionPane.showInputDialog("Insert password");
				if (password == null || password.length() == 0)
				{
					JOptionPane.showMessageDialog(this,"Please enter password");
					return;
				}

				try {
					byteMessage = Send.signFunction(message, idPrivate, password);
				} catch (PasswordError e) {
					JOptionPane.showMessageDialog(this,"Please enter correct password");
					return;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this,"Shit happens");
					return;
				}
			}
			if (byteMessage == null)
				byteMessage = message.getBytes();
			if (encryptFlag) {
				if ((String) publicDataTable.getValueAt(publicDataTable.getSelectedRow(),0) == "-1")
				{
					JOptionPane.showMessageDialog(this,"Please choose private key");
					return;
				}
				Long idPublic = Long.parseLong((String) publicDataTable.getValueAt(publicDataTable.getSelectedRow(),0));
				
				byteMessage = Send.encryptFunction(byteMessage, idPublic, false);
			}
		});
	}
	
	public void loadPublicTable() {
		String[] columns = {"Key Id", "User info", "Public"};
		String data[][] = new String[Key.numberOfKeys()][4];
		int i=0;
		for(Pair<Long, String> p: Key.getAllKeyRing()) {
			
			char c = p.getValue().charAt(p.getValue().length()-1);
			if (c == '1' || c == '3')
				data[i][2] = "true";
			else
				continue;
			data[i][0] = p.getKey().toString();
			
			data[i][1] = p.getValue().substring(0, p.getValue().length()-1);
			
			i++;
		}
		if (i>0) {
			publicDataTable = new JTable(data, columns);
			add(publicDataTable);
		}
		
	}
	
	public void loadPrivateTable() {
		String[] columns = {"Key Id", "User info", "Private"};
		String data[][] = new String[Key.numberOfKeys()][4];
		int i=0;
		for(Pair<Long, String> p: Key.getAllKeyRing()) {
			
			char c = p.getValue().charAt(p.getValue().length()-1);
			if (c == '2' || c == '3')
				data[i][2] = "true";
			else
				continue;
			data[i][0] = p.getKey().toString();
			
			data[i][1] = p.getValue().substring(0, p.getValue().length()-1);
			
			i++;
		}
		if (i>0) {
			privateDataTable = new JTable(data, columns);
			add(privateDataTable);
		}
	}
}
