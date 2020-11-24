package se.plweb.memory.gui;

import java.awt.GridBagConstraints;
import javax.swing.JPanel;

/**
 * @author Peter Lindblom
 * 
 */

public abstract class AbstractPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected static GridBagConstraints fixLayout(int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipadx = 10;
		gbc.ipady = 10;
		return gbc;
	}
}
