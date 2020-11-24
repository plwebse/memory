package se.plweb.memory.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 * @author Peter Lindblom
 * 
 */

public class CommonGameFunctions {

	protected static void applyGridBagConstraints(JPanel panel,
			GameBoardGui gameBoard, JPanel statusPanel1, JPanel statusPanel2) {
		GridBagLayout gbl = new GridBagLayout();

		GridBagConstraints gameBoardGbc = new GridBagConstraints();
		gameBoardGbc.gridx = 0;
		gameBoardGbc.gridy = 0;
		gameBoardGbc.gridwidth = 2;
		gameBoardGbc.weightx = 1;
		gameBoardGbc.weighty = 1;
		gameBoardGbc.gridheight = 2;
		gameBoardGbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(gameBoard, gameBoardGbc);

		GridBagConstraints statusPanel1Gbc = new GridBagConstraints();
		statusPanel1Gbc.gridx = 3;
		statusPanel1Gbc.gridy = 0;
		statusPanel1Gbc.gridwidth = 1;
		statusPanel1Gbc.gridheight = 1;
		statusPanel1Gbc.weightx = 1;
		statusPanel1Gbc.weighty = 1;
		statusPanel1Gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(statusPanel1, statusPanel1Gbc);

		GridBagConstraints statusPanel2Gbc = new GridBagConstraints();
		statusPanel2Gbc.gridx = 3;
		statusPanel2Gbc.gridy = 1;
		statusPanel2Gbc.gridwidth = 1;
		statusPanel2Gbc.gridheight = 1;
		statusPanel2Gbc.weightx = 1;
		statusPanel2Gbc.weighty = 1;
		statusPanel2Gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(statusPanel2, statusPanel2Gbc);

		panel.add(gameBoard);
		panel.add(statusPanel1);
		panel.add(statusPanel2);
		panel.setLayout(gbl);
	}
}
