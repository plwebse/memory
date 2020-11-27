package se.plweb.memory.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * @author Peter Lindblom
 */

public class GuiMenuBar extends JMenuBar implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private final JMenuItem miExit = new JMenuItem("Exit", KeyEvent.VK_X);
	private final JMenuItem miAbout = new JMenuItem("About", KeyEvent.VK_A);
	private final JMenuItem miNewTwoPlayerVsNetwork = new JMenuItem(
			"Player vs network player", KeyEvent.VK_N);
	private final JMenuItem miNewTwoPlayerVsComputer = new JMenuItem(
			"Player vs computer", KeyEvent.VK_C);
	private final JMenuItem miNewSinglePlayer = new JMenuItem("Single player",
			KeyEvent.VK_S);
	private final Gui gui;
	
	public GuiMenuBar(Gui gui) {
		super();		
		this.gui = gui;

		JMenu mFile = new JMenu("File");
		mFile.setMnemonic(KeyEvent.VK_F); // show the menu

		miNewSinglePlayer.addActionListener(this);
		miNewTwoPlayerVsNetwork.addActionListener(this);
		miNewTwoPlayerVsComputer.addActionListener(this);
		miAbout.addActionListener(this);
		miExit.addActionListener(this);

		mFile.add(miNewSinglePlayer);
		mFile.add(miNewTwoPlayerVsNetwork);
		mFile.add(miNewTwoPlayerVsComputer);
		mFile.add(miAbout);
		mFile.add(miExit);
		this.add(mFile);
	}
	
	
	public void actionPerformed(ActionEvent event) {		
		if (event.getSource() == miExit) {
			gui.stopApplication();
		} else if (event.getSource() == miNewSinglePlayer) {			
			gui.showPanel(GamePanel.singlePlayerPanel);			
		} else if (event.getSource() == miAbout) {
			gui.showPanel(GamePanel.aboutTheGamePanel);
		} else if (event.getSource() == miNewTwoPlayerVsNetwork) {
			gui.showPanel(GamePanel.playerVsNetworkPlayerPanel);
		} else if (event.getSource() == miNewTwoPlayerVsComputer) {
			gui.showPanel(GamePanel.playerVsComputerPlayerPanel);
		}
	}
}
