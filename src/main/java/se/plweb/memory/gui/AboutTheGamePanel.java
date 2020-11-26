package se.plweb.memory.gui;

import java.awt.GridBagLayout;
import javax.swing.JLabel;

/**
 * @author Peter Lindblom
 * 
 */

public class AboutTheGamePanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("SpellCheckingInspection")
	public AboutTheGamePanel() {

		JLabel lAbout = new JLabel("Made by Peter Lindblom 2010");
		GridBagLayout gblAbout = new GridBagLayout();
		gblAbout.setConstraints(lAbout, fixLayout(0));
		this.add(lAbout);
		JLabel lAboutUrl = new JLabel("http://www.plweb.se");
		gblAbout.setConstraints(lAboutUrl, fixLayout(1));
		this.add(lAboutUrl);
		this.setLayout(gblAbout);
	}
}
