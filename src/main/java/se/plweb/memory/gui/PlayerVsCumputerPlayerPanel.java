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
	private final JComboBox<GameBoardDimension> cSelectSize = new JComboBox<>(GameBoardDimension.values());
	private final String[] sDifficulties = getComputerPlayersDifficulties();
	private final ComputerPlayers[] difficulties = ComputerPlayers.values();
	private final JComboBox<String> cSelectDifficulty = new JComboBox<>(sDifficulties);
	private final JButton bOk = new JButton("OK");
	private Gui gui = null;

	public PlayerVsCumputerPlayerPanel(Gui gui) {

		this.gui = gui;

		JLabel lHeading = new JLabel("New player vs computer player game",
				JLabel.CENTER);
		GridBagLayout gblMulti = new GridBagLayout();
		gblMulti.setConstraints(lHeading, fixLayout(0));
		this.add(lHeading);

		JLabel lSelectSize = new JLabel("select size", JLabel.CENTER);
		gblMulti.setConstraints(lSelectSize, fixLayout(1));
		this.add(lSelectSize);

		gblMulti.setConstraints(cSelectSize, fixLayout(2));
		this.add(cSelectSize);

		JLabel lDifficulty = new JLabel("select difficulty", JLabel.CENTER);
		gblMulti.setConstraints(lDifficulty, fixLayout(3));
		this.add(lDifficulty);

		gblMulti.setConstraints(cSelectDifficulty, fixLayout(4));
		this.add(cSelectDifficulty);

		gblMulti.setConstraints(bOk, fixLayout(5));
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
