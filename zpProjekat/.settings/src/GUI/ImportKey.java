package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import LOGIC.User;

public class ImportKey extends JPanel {
	private User user;
	private JLabel label = new JLabel("Chose key from .asc file");
	private JFileChooser fileChooser = new JFileChooser();
	private JButton chooseButton = new JButton("Choose");
	private JButton importButton = new JButton("Import");
	private JPanel up = new JPanel();
	private JPanel down = new JPanel();
	
	public ImportKey(User user){
		this.user = user;
		importButton.setEnabled(false);
		setLayout(new GridLayout(4,1));

		up.add(label);
		up.add(chooseButton);
		add(up);
		down.add(importButton);
		add(down);
		
		chooseButton.addActionListener(l ->{
			fileChooser.showOpenDialog(this);
			importButton.setEnabled(true);
		});
	}

}
