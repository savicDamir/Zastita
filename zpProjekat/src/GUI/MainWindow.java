package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

public class MainWindow extends JFrame {

	private JMenu menu;
	private JMenuBar menuBar;
	private JMenuItem newKey, importKey, exportKey;
	private ImportKey importKeyPanel;
	private ExportKey exportKeyPanel;
	private JPanel panel = new JPanel();
	public MainWindow() {
		importKeyPanel = new ImportKey();
		exportKeyPanel = new ExportKey();
		//add(importKeyPanel);
		menuBar = new JMenuBar();
		menu = new JMenu("menu");
		
		menuBar.add(menu);

		newKey = new JMenuItem("Generate new key");
		newKey.addActionListener(l -> {
			importKeyPanel.setVisible(false);
			exportKeyPanel.setVisible(false);
		});
		menu.add(newKey);

		importKey = new JMenuItem("Import key");
		importKey.addActionListener(l -> {
			setPanel(importKeyPanel);
		});
		menu.add(importKey);
		
		exportKey = new JMenuItem("Export key");
		exportKey.addActionListener(l -> {
			setPanel(exportKeyPanel);
		});
		
		menu.add(exportKey);
		
		setJMenuBar(menuBar);
        setVisible(true);
        setSize(400, 400);
        setLocation(400, 300);
		
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
	
	private void setPanel(JPanel panel) {
		getContentPane().removeAll();
		add(panel);
		validate();
		repaint();
	}
}
