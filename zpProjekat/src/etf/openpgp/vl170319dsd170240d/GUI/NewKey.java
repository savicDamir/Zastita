package etf.openpgp.vl170319dsd170240d.GUI;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import etf.openpgp.vl170319dsd170240d.LOGIC.*;

public class NewKey extends JPanel {

	private User user;
	
	private JPanel panel[] = new JPanel[5];
	private JLabel name = new JLabel("Name: ");
	private JLabel email = new JLabel("Email: ");
	private JLabel type = new JLabel("Type: ");
	private JButton generate = new JButton("Generate");
	
	private String[] s1 = {"1024", "2048", "4098"};
	private JTextField nameField = new JTextField(15);
	private JTextField emailField = new JTextField(15);
	private JComboBox combo = new JComboBox(s1);
	public NewKey(User user) {
		this.user = user;
	
		setLayout(new GridLayout(5,1));
		panel[0] = new JPanel();
		panel[0].add(name);
		panel[0].add(nameField);
		panel[1] = new JPanel();
		panel[1].add(email);
		panel[1].add(emailField);
		panel[2] = new JPanel();
		panel[2].add(type);
		panel[2].add(combo);
		panel[3] = new JPanel();
		panel[3].add(generate);
		
		add(panel[0]);
		add(panel[1]);
		add(panel[2]);
		add(panel[3]);
		
		generate.addActionListener(l -> {
			if (nameField.getText().length() == 0 || emailField.getText().length() == 0)
			{
				JOptionPane.showMessageDialog(this,"Please enter your name and email before generating new key");
				return;
			}
			String password = JOptionPane.showInputDialog("Insert password");
			if (password == null || password.length() == 0)
			{
				JOptionPane.showMessageDialog(this,"Please enter password");
				return;
			}
			user.generateNewKey(nameField.getText(), emailField.getText(), combo.getSelectedItem().toString(), password);
			System.out.println("uspeh uspelo je");
		});
	}
}
