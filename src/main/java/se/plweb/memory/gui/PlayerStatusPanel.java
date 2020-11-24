package se.plweb.memory.gui;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Peter Lindblom
 */

public class PlayerStatusPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel playerName = new JLabel("", JLabel.CENTER);
	private JLabel playerStatus = new JLabel("", JLabel.CENTER);
	private JLabel playerPairStatus = new JLabel("0 / 0", JLabel.CENTER);
	private JLabel playerAttempts = new JLabel("0", JLabel.CENTER);
	private JLabel playerEmpty = new JLabel("", JLabel.CENTER);

	public PlayerStatusPanel(String name) {
		this.add(playerName);
		this.add(playerStatus);
		this.add(playerPairStatus);
		this.add(playerAttempts);
		this.add(playerEmpty);
		this.add(playerEmpty);		
		this.setLayout(new GridLayout(0, 1));
		playerName.setText(name);
	}

	public void updateStatus(String text) {
		playerStatus.setText(text);
		updatePanel();
	}

	public void updatePairStatus(int matchedPairs, int totalNumberOfPairs) {
		playerPairStatus.setText(matchedPairs + " / " + totalNumberOfPairs);
		updatePanel();
	}

	public void updateAttempts(int totalNumberOfAttempts) {
		playerAttempts.setText(totalNumberOfAttempts + "");
		updatePanel();
	}

	private void updatePanel(){
		repaint();
	}
}
