package etf.openpgp.vl170319dsd170240d.GUI;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import etf.openpgp.vl170319dsd170240d.LOGIC.*;

public class ImportKey extends JPanel {
	private User user;
	private JLabel label = new JLabel("Chose key from .asc file");
	private JFileChooser fileChooser = new JFileChooser();
	private JButton chooseButton = new JButton("Choose");
	private JButton importButton = new JButton("Import");
	private JPanel up = new JPanel();
	private JPanel down = new JPanel();
	private File file;
	
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
			file = fileChooser.getSelectedFile();
			
			importButton.setEnabled(true);
		});
		
		importButton.addActionListener(l -> {
			if(Key.importKey(file.getPath())) {
				JOptionPane.showMessageDialog(this,"Key has been imported");
			}
			else {
				JOptionPane.showMessageDialog(this,"Error");
			}
		});
	}

}
