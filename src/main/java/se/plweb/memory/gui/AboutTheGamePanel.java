package se.plweb.memory.gui;

import java.awt.GridBagLayout;
import javax.swing.JLabel;

/**
 * @author Peter Lindblom
 * 
 */

public class AboutTheGamePanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private GridBagLayout gblAbout = new GridBagLayout();
	private JLabel lAbout = new JLabel("Made by Peter Lindblom 2010");
	private JLabel lAboutUrl = new JLabel("http://www.plweb.se");

	public AboutTheGamePanel() {

		gblAbout.setConstraints(lAbout, fixLayout(0, 0));
		this.add(lAbout);
		gblAbout.setConstraints(lAboutUrl, fixLayout(0, 1));
		this.add(lAboutUrl);
		this.setLayout(gblAbout);
	}
}
