package se.plweb.memory.gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import se.plweb.memory.domain.ComputerPlayers;

/**
 * @author Peter Lindblom
 * 
 */

public class PlayerVsCumputerPlayerPanel extends AbstractPanel implements
		ActionListener {

	private static final long serialVersionUID = 1L;
	private GridBagLayout gblMulti = new GridBagLayout();
	private JLabel lHeading = new JLabel("New player vs computer player game",
			JLabel.CENTER);
	private JLabel lSelectSize = new JLabel("select size", JLabel.CENTER);
	private JComboBox cSelectSize = new JComboBox(GameBoardDimension.values());
	private JLabel lDifficulty = new JLabel("select difficulty", JLabel.CENTER);
	private String[] sDifficulties = getComputerPlayersDifficulties();
	private ComputerPlayers[] difficulties = ComputerPlayers.values();
	private JComboBox cSelectDifficulty = new JComboBox(sDifficulties);
	private JButton bOk = new JButton("OK");
	private Gui gui = null;

	public PlayerVsCumputerPlayerPanel(Gui gui) {

		this.gui = gui;

		gblMulti.setConstraints(lHeading, fixLayout(0, 0));
		this.add(lHeading);

		gblMulti.setConstraints(lSelectSize, fixLayout(0, 1));
		this.add(lSelectSize);

		gblMulti.setConstraints(cSelectSize, fixLayout(0, 2));
		this.add(cSelectSize);

		gblMulti.setConstraints(lDifficulty, fixLayout(0, 3));
		this.add(lDifficulty);

		gblMulti.setConstraints(cSelectDifficulty, fixLayout(0, 4));
		this.add(cSelectDifficulty);

		gblMulti.setConstraints(bOk, fixLayout(0, 5));
		this.add(bOk);

		bOk.addActionListener(this);
		this.setLayout(gblMulti);

	}

	private String[] getComputerPlayersDifficulties() {

		String[] output = new String[ComputerPlayers.values().length];
		ComputerPlayers[] computerPlayers = ComputerPlayers.values();

		for (int i = 0; i < ComputerPlayers.values().length; i++) {
			output[i] = computerPlayers[i].getDescription();
		}

		return output;

	}

	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == bOk) {
			gui.startNewPlayerVsComputerPlayer(
					GameBoardDimension.values()[cSelectSize.getSelectedIndex()]
							.getXSize(),
					GameBoardDimension.values()[cSelectSize.getSelectedIndex()]
							.getYSize(), difficulties[cSelectDifficulty
							.getSelectedIndex()]);
		}
	}
}
