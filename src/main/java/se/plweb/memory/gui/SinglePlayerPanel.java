package se.plweb.memory.gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * @author Peter Lindblom
 * 
 */

public class SinglePlayerPanel extends AbstractPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private GridBagLayout gblSingle = new GridBagLayout();
	private JLabel lSingleHeading = new JLabel("New single player game",
			JLabel.CENTER);
	private JLabel lSingleSelectSize = new JLabel("select size", JLabel.CENTER);
	private JComboBox cSingleSize = new JComboBox(GameBoardDimension.values());
	private JButton bSingleOk = new JButton("OK");
	private Gui gui = null;

	public SinglePlayerPanel(Gui gui) {

		this.gui = gui;

		gblSingle.setConstraints(lSingleHeading, fixLayout(0, 0));
		this.add(lSingleHeading);
		gblSingle.setConstraints(lSingleSelectSize, fixLayout(0, 1));
		this.add(lSingleSelectSize);
		gblSingle.setConstraints(cSingleSize, fixLayout(0, 2));
		this.add(cSingleSize);
		gblSingle.setConstraints(bSingleOk, fixLayout(0, 3));
		this.add(bSingleOk);
		bSingleOk.addActionListener(this);
		this.setLayout(gblSingle);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == bSingleOk) {
			gui.startNewSinglePlayerGame(
					GameBoardDimension.values()[cSingleSize.getSelectedIndex()]
							.getXSize(),
					GameBoardDimension.values()[cSingleSize.getSelectedIndex()]
							.getYSize());
		}
	}
}
