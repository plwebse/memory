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

	private final JLabel playerStatus = new JLabel("", JLabel.CENTER);
	private final JLabel playerPairStatus = new JLabel("0 / 0", JLabel.CENTER);
	private final JLabel playerAttempts = new JLabel("0", JLabel.CENTER);

	public PlayerStatusPanel(String name) {
		JLabel playerName = new JLabel("", JLabel.CENTER);
		this.add(playerName);
		this.add(playerStatus);
		this.add(playerPairStatus);
		this.add(playerAttempts);
		JLabel playerEmpty = new JLabel("", JLabel.CENTER);
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
		playerPairStatus.setText(String.format("%d / %d", matchedPairs, totalNumberOfPairs));
		updatePanel();
	}

	public void updateAttempts(int totalNumberOfAttempts) {
		playerAttempts.setText(String.format("%d", totalNumberOfAttempts));
		updatePanel();
	}

	private void updatePanel(){
		repaint();
	}
}
