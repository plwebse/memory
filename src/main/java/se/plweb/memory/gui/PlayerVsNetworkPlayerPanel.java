package se.plweb.memory.gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Peter Lindblom
 * 
 */

public class PlayerVsNetworkPlayerPanel extends AbstractPanel implements
		ActionListener {

	private static final long serialVersionUID = 1L;
	private final JComboBox<GameBoardDimension> cMultiSize = new JComboBox<>(GameBoardDimension.values());

	private final String[] sMultiClientOrServer = { "Client", "Server" };
	private final JComboBox<String> cMultiClientOrServer = new JComboBox<>(sMultiClientOrServer);
	private final JTextField tMultiIPNumber = new JTextField("localhost");
	private final JTextField tMultiPortNumber = new JTextField("6777");
	private Gui gui;

	public PlayerVsNetworkPlayerPanel(Gui gui) {

		this.gui = gui;

		JLabel lMultiHeading = new JLabel(
				"New player vs network player game", JLabel.CENTER);
		GridBagLayout gblMulti = new GridBagLayout();
		gblMulti.setConstraints(lMultiHeading, fixLayout(0));
		this.add(lMultiHeading);
		JLabel lMultiSelectClientOrServer = new JLabel(
				"select client or server", JLabel.CENTER);
		gblMulti.setConstraints(lMultiSelectClientOrServer, fixLayout(1));
		this.add(lMultiSelectClientOrServer);
		gblMulti.setConstraints(cMultiClientOrServer, fixLayout(2));
		this.add(cMultiClientOrServer);
		JLabel lMultiSelectSize = new JLabel("select size", JLabel.CENTER);
		gblMulti.setConstraints(lMultiSelectSize, fixLayout(3));
		this.add(lMultiSelectSize);
		gblMulti.setConstraints(cMultiSize, fixLayout(4));
		this.add(cMultiSize);
		JLabel lMultiIPNumber = new JLabel("enter ip number", JLabel.CENTER);
		gblMulti.setConstraints(lMultiIPNumber, fixLayout(5));
		this.add(lMultiIPNumber);
		gblMulti.setConstraints(tMultiIPNumber, fixLayout(6));
		this.add(tMultiIPNumber);
		JLabel lMultiPortNumber = new JLabel("enter port number",
				JLabel.CENTER);
		gblMulti.setConstraints(lMultiPortNumber, fixLayout(7));
		this.add(lMultiPortNumber);
		gblMulti.setConstraints(tMultiPortNumber, fixLayout(8));
		this.add(tMultiPortNumber);
		JButton bMultiOk = new JButton("OK");
		gblMulti.setConstraints(bMultiOk, fixLayout(9));
		this.add(bMultiOk);
		cMultiClientOrServer.addActionListener(this);
		bMultiOk.addActionListener(this);
		this.setLayout(gblMulti);
		updateMultiPanel();

	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == cMultiClientOrServer) {
			updateMultiPanel();
		} else if (sMultiClientOrServer[cMultiClientOrServer.getSelectedIndex()]
				.equals("Server")) {
			gui.startNewPlayerVsNetworkPlayerServer(
					GameBoardDimension.values()[cMultiSize.getSelectedIndex()]
							.getXSize(), GameBoardDimension.values()[cMultiSize
							.getSelectedIndex()].getYSize(), Integer
							.parseInt(tMultiPortNumber.getText()));
		} else if (sMultiClientOrServer[cMultiClientOrServer.getSelectedIndex()]
				.equals("Client")) {
			gui.startNewPlayerVsNetworkPlayerClient(tMultiIPNumber.getText(),
					Integer.parseInt(tMultiPortNumber.getText()));
		}
	}

	private void updateMultiPanel() {
		if (sMultiClientOrServer[cMultiClientOrServer.getSelectedIndex()]
				.equals("Server")) {
			cMultiSize.setEnabled(true);
		} else if (sMultiClientOrServer[cMultiClientOrServer.getSelectedIndex()]
				.equals("Client")) {
			cMultiSize.setEnabled(false);
		}
	}
}
