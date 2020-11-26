package se.plweb.memory.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import se.plweb.memory.domain.*;

/**
 * 
 * @author Peter Lindblom
 */

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	// global
	private final ThreadControl threadControl = ThreadControl.getInstance();
	// cardLayout
	private final JPanel pCards = new JPanel(new CardLayout());
	private final CardLayout cardLayout = (CardLayout) (pCards.getLayout());
	// meny

	// game panel
	private GameSinglePlayer panelGameSinglePlayer = null;
	private GamePlayerVsNetworkPlayer panelGamePlayerVsNetworkPlayer = null;
	private GamePlayerVsComputerPlayer panelGamePlayerVsComputerPlayer = null;

	private static final String singlePlayerGameCard = "singlePlayerGameCard";
	private static final String playerVsNetworkPlayerGameCard = "playerVsNetworkPlayerGameCard";
	private static final String playerVsComputerPlayerGameCard = "playerVsComputerPlayerGameCard";

	public Gui(String jFrameTitle) {
		GuiMenuBar guiMenuBar = new GuiMenuBar(this);
		this.setJMenuBar(guiMenuBar);

		SinglePlayerPanel singlePlayerPanel = new SinglePlayerPanel(this);
		pCards.add(singlePlayerPanel, GamePanel.singlePlayerPanel.toString());
		PlayerVsNetworkPlayerPanel playerVsNetworkPlayerPanel = new PlayerVsNetworkPlayerPanel(
				this);
		pCards.add(playerVsNetworkPlayerPanel, GamePanel.playerVsNetworkPlayerPanel.toString());
		PlayerVsCumputerPlayerPanel playerVsComputerPlayerPanel = new PlayerVsCumputerPlayerPanel(
				this);
		pCards.add(playerVsComputerPlayerPanel, GamePanel.playerVsComputerPlayerPanel.toString());
		AboutTheGamePanel aboutTheGamePanel = new AboutTheGamePanel();
		pCards.add(aboutTheGamePanel, GamePanel.aboutTheGamePanel.toString());
		pCards.setBorder(new BevelBorder(BevelBorder.LOWERED));

		Dimension minimumSize = new Dimension(800, 600);
		this.setSize(minimumSize);
		this.setMinimumSize(minimumSize);
		this.setPreferredSize(minimumSize);
		this.add(pCards);
		this.setTitle(jFrameTitle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pack();
		this.setFocusable(true);
		this.setEnabled(true);		
		this.setVisible(true);	
	}

	public void showPanel(GamePanel gamePanel){		
		cardLayout.show(pCards, gamePanel.toString());	
	}
	
	public void startNewPlayerVsNetworkPlayerClient(String ipNumber,
			int listenToPortNumber) {
		stopAll();
		removeOldGame();
		setPanelGamePlayerVsNetworkPlayer(new GamePlayerVsNetworkPlayer(this,
				ipNumber, listenToPortNumber));
		pCards.add(getPanelGamePlayerVsNetworkPlayer(),
				playerVsNetworkPlayerGameCard);
		cardLayout.show(pCards, playerVsNetworkPlayerGameCard);
		
		SwingUtilities.invokeLater(() -> getPanelGamePlayerVsNetworkPlayer().requestFocusInWindow());
	}

	public void startNewPlayerVsNetworkPlayerServer(int xSize, int ySize,
			int listenToPortNumber) {
		stopAll();
		removeOldGame();
		setPanelGamePlayerVsNetworkPlayer(new GamePlayerVsNetworkPlayer(xSize,
				ySize, this, listenToPortNumber));
		pCards.add(getPanelGamePlayerVsNetworkPlayer(),
				playerVsNetworkPlayerGameCard);
		cardLayout.show(pCards, playerVsNetworkPlayerGameCard);
		
		SwingUtilities.invokeLater(() -> getPanelGamePlayerVsNetworkPlayer().requestFocusInWindow());
	}

	public void startNewPlayerVsComputerPlayer(int xSize, int ySize,
			ComputerPlayers difficulty) {
		stopAll();
		removeOldGame();
		setPanelGamePlayerVsComputerPlayer(new GamePlayerVsComputerPlayer(this,
				xSize, ySize, difficulty));
		pCards.add(getPanelGamePlayerVsComputerPlayer(),
				playerVsComputerPlayerGameCard);
		cardLayout.show(pCards, playerVsComputerPlayerGameCard);
		
		SwingUtilities.invokeLater(() -> getPanelGamePlayerVsComputerPlayer().requestFocusInWindow());
	}

	public void startNewSinglePlayerGame(int xSize, int ySize) {
		this.stopAll();
		removeOldGame();

		setPanelGameSinglePlayer(new GameSinglePlayer(this, xSize, ySize));		

		pCards.add(getPanelGameSinglePlayer(), singlePlayerGameCard);
		cardLayout.show(pCards, singlePlayerGameCard);	
		
		SwingUtilities.invokeLater(() -> getPanelGameSinglePlayer().requestFocusInWindow());
	}

	private void removeOldGame() {
		if (getPanelGamePlayerVsNetworkPlayer() != null) {
			pCards.remove(getPanelGamePlayerVsNetworkPlayer());
			setPanelGamePlayerVsNetworkPlayer(null);
		}

		if (getPanelGameSinglePlayer() != null) {
			pCards.remove(getPanelGameSinglePlayer());
			setPanelGameSinglePlayer(null);
		}

		if (getPanelGamePlayerVsComputerPlayer() != null) {
			pCards.remove(getPanelGamePlayerVsComputerPlayer());
			setPanelGamePlayerVsComputerPlayer(null);
		}
	}

	public void startServer(
			GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer, int port,
			int xSize, int ySize) {
		stopAll();
		threadControl.startWaitForClient(gamePlayerVsNetworkPlayer, port,
				xSize, ySize);
	}

	public void startClient(
			GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer, String ip,
			int port) {
		stopAll();
		threadControl.startClient(gamePlayerVsNetworkPlayer, ip, port);
	}

	public void startPlayerVsComputerPlayer(
			GamePlayerVsComputerPlayer gamePlayerVsComputerPlayer) {
		stopAll();
		threadControl.startPlayerVsComputer(gamePlayerVsComputerPlayer);
	}

	public void startSinglePlayer(GameSinglePlayer gameSinglePlayer) {
		stopAll();
		threadControl.startSinglePlayer(gameSinglePlayer);
	}

	private void stopAll() {
		threadControl.stopAll();
	}

	private GameSinglePlayer getPanelGameSinglePlayer() {
		return panelGameSinglePlayer;
	}

	private void setPanelGameSinglePlayer(GameSinglePlayer panelGameSinglePlayer) {
		this.panelGameSinglePlayer = panelGameSinglePlayer;
	}

	private GamePlayerVsNetworkPlayer getPanelGamePlayerVsNetworkPlayer() {
		return panelGamePlayerVsNetworkPlayer;
	}

	private void setPanelGamePlayerVsNetworkPlayer(
			GamePlayerVsNetworkPlayer panelGamePlayerVsNetworkPlayer) {
		this.panelGamePlayerVsNetworkPlayer = panelGamePlayerVsNetworkPlayer;
	}

	private GamePlayerVsComputerPlayer getPanelGamePlayerVsComputerPlayer() {
		return panelGamePlayerVsComputerPlayer;
	}

	private void setPanelGamePlayerVsComputerPlayer(
			GamePlayerVsComputerPlayer panelGamePlayerVsComputerPlayer) {
		this.panelGamePlayerVsComputerPlayer = panelGamePlayerVsComputerPlayer;
	}

}
