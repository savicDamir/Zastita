package etf.openpgp.vl170319dsd170240d.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import etf.openpgp.vl170319dsd170240d.LOGIC.*;

public class MainWindow extends JFrame {

	private JMenu menu;
	private JMenuBar menuBar;
	private JMenuItem newKey, importKey, exportKey, sendMessage, save;
	private NewKey newKeyPanel;
	private ImportKey importKeyPanel;
	private ExportKey exportKeyPanel;
	//private SendMessage sendMessagePanel;
	private JPanel panel = new JPanel();
	
	private User user;
	
	public MainWindow() {
		User user = new User();
		newKeyPanel = new NewKey(user);
		importKeyPanel = new ImportKey(user);
		exportKeyPanel = new ExportKey(user);
		//sendMessagePanel = new SendMessage();
		setPanel(exportKeyPanel);
		
		menuBar = new JMenuBar();
		menu = new JMenu("menu");
		
		menuBar.add(menu);

		newKey = new JMenuItem("Generate new key");
		newKey.addActionListener(l -> {
			setPanel(newKeyPanel);
		});
		menu.add(newKey);

		importKey = new JMenuItem("Import key");
		importKey.addActionListener(l -> {
			setPanel(importKeyPanel);
		});
		menu.add(importKey);
		
		exportKey = new JMenuItem("Export key");
		exportKey.addActionListener(l -> {
			exportKeyPanel = new ExportKey(user);
			setPanel(exportKeyPanel);
		});
		menu.add(exportKey);
		
		sendMessage = new JMenuItem("Send message");
		sendMessage.addActionListener(l ->{
			//sendMessagePanel = new SendMessage();
			//setPanel(sendMessagePanel);
		});
		menu.add(sendMessage);

		
		save = new JMenuItem("Save");
		save.addActionListener(l ->{
			Key.storeAllKeyRings();
		});
		menu.add(save);
		
		setJMenuBar(menuBar);
        setVisible(true);
        setSize(400, 400);
        setLocation(400, 300);
		
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
	
	public static void main(String[] args) {
		Key.loadAllKeyRings();
		
		new MainWindow();
		
	}
	
	private void setPanel(JPanel panel) {
		getContentPane().removeAll();
		add(panel);
		validate();
		repaint();
	}
}
