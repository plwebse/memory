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
	private GridBagLayout gblMulti = new GridBagLayout();
	private JLabel lMultiHeading = new JLabel(
			"New player vs network player game", JLabel.CENTER);
	private JLabel lMultiSelectSize = new JLabel("select size", JLabel.CENTER);
	private JComboBox cMultiSize = new JComboBox(GameBoardDimension.values());
	private JButton bMultiOk = new JButton("OK");

	private JLabel lMultiSelectClientOrServer = new JLabel(
			"select client or server", JLabel.CENTER);
	private JLabel lMultiIPNumber = new JLabel("enter ip number", JLabel.CENTER);
	private JLabel lMultiPortNumber = new JLabel("enter port number",
			JLabel.CENTER);
	private String[] sMultiClientOrServer = { "Client", "Server" };
	private JComboBox cMultiClientOrServer = new JComboBox(sMultiClientOrServer);
	private JTextField tMultiIPNumber = new JTextField("localhost");
	private JTextField tMultiPortNumber = new JTextField("6777");
	private Gui gui = null;

	public PlayerVsNetworkPlayerPanel(Gui gui) {

		this.gui = gui;

		gblMulti.setConstraints(lMultiHeading, fixLayout(0, 0));
		this.add(lMultiHeading);
		gblMulti.setConstraints(lMultiSelectClientOrServer, fixLayout(0, 1));
		this.add(lMultiSelectClientOrServer);
		gblMulti.setConstraints(cMultiClientOrServer, fixLayout(0, 2));
		this.add(cMultiClientOrServer);
		gblMulti.setConstraints(lMultiSelectSize, fixLayout(0, 3));
		this.add(lMultiSelectSize);
		gblMulti.setConstraints(cMultiSize, fixLayout(0, 4));
		this.add(cMultiSize);
		gblMulti.setConstraints(lMultiIPNumber, fixLayout(0, 5));
		this.add(lMultiIPNumber);
		gblMulti.setConstraints(tMultiIPNumber, fixLayout(0, 6));
		this.add(tMultiIPNumber);
		gblMulti.setConstraints(lMultiPortNumber, fixLayout(0, 7));
		this.add(lMultiPortNumber);
		gblMulti.setConstraints(tMultiPortNumber, fixLayout(0, 8));
		this.add(tMultiPortNumber);
		gblMulti.setConstraints(bMultiOk, fixLayout(0, 9));
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
