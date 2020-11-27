package se.plweb.memory.gui;

import java.util.logging.Logger;
import javax.swing.JPanel;
import se.plweb.memory.domain.*;

/**
 * 
 * @author Peter Lindblom
 */

public class GamePlayerVsNetworkPlayer extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Logger logger;

	private final GameBoardGui gameBoard = new GameBoardGui();
	private final PlayerStatusPanel playerStatusPanelServer = new PlayerStatusPanel(
			"Server");
	private final PlayerStatusPanel playerStatusPanelClient = new PlayerStatusPanel(
			"Client");
	private final Gui gui;
	private final ThreadControl threadControl = ThreadControl.getInstance();

	public GamePlayerVsNetworkPlayer(int xSize, int ySize, Gui gui, int port) {
		logger = Logger.getLogger(this.getClass().getName());

		logger.fine("Server x=" + xSize + " y=" + ySize + " port="
				+ port);
		// Server
		this.gameBoard.makeGameBoard(xSize, ySize);
		this.gui = gui;

		CommonGameFunctions.applyGridBagConstraints(this, gameBoard,
				playerStatusPanelServer, playerStatusPanelClient);

		this.gui.startServer(this, port, xSize, ySize);
	}

	public GamePlayerVsNetworkPlayer(Gui gui, String ip, int port) {
		logger = Logger.getLogger(this.getClass().getName());

		logger.fine("Client ip=" + ip + " port=" + port);

		// Client
		this.gui = gui;

		CommonGameFunctions.applyGridBagConstraints(this, gameBoard,
				playerStatusPanelServer, playerStatusPanelClient);

		this.gui.startClient(this, ip, port);
	}

	public void waitingForClient() {
		String messageWaitingForClient = "waiting for client...";
		playerStatusPanelClient.updateStatus(messageWaitingForClient);
		playerStatusPanelServer.updateStatus(messageWaitingForClient);
	}

	public void notConnected() {
		String messageNotConnected = "Not connected...";
		playerStatusPanelClient.updateStatus(messageNotConnected);
		playerStatusPanelServer.updateStatus(messageNotConnected);
	}

	public int getMatchedPairs() {
		logger.fine("getMatchedPairs="
				+ gameBoard.getNumberOfMatchedPairs());
		return gameBoard.getNumberOfMatchedPairs();
	}

	public int getTotalNumberOfAttempts() {
		logger.fine("getMatchedPairs="
				+ gameBoard.getTotalNumberOfAttempts());
		return gameBoard.getTotalNumberOfAttempts();
	}

	public int getTotalNumberOfParis() {
		logger.fine("getNumberOfParis"
				+ gameBoard.getTotalNumberOfPairs());
		return gameBoard.getTotalNumberOfPairs();
	}

	public void updateStatusClient(int matchedPairs, int numberOfAttempts) {
		logger.fine("" + matchedPairs + " / "
				+ this.getTotalNumberOfParis());
		playerStatusPanelClient.updateStatus("");
		playerStatusPanelClient.updatePairStatus(matchedPairs, this
				.getTotalNumberOfParis());
		playerStatusPanelClient.updateAttempts(numberOfAttempts);
	}

	public void updateStatusServer(int matchedPairs, int numberOfAttempts) {
		logger.fine("" + matchedPairs + " / "
				+ this.getTotalNumberOfParis());
		playerStatusPanelServer.updateStatus("");
		playerStatusPanelServer.updatePairStatus(matchedPairs, this
				.getTotalNumberOfParis());
		playerStatusPanelServer.updateAttempts(numberOfAttempts);
	}

	public void clientWon() {
		String messageClientWon = "Server won";
		playerStatusPanelClient.updateStatus(messageClientWon);
		playerStatusPanelServer.updateStatus(messageClientWon);
		playerStatusPanelClient.updatePairStatus(0, 0);
		playerStatusPanelServer.updatePairStatus(0, 0);
		gameBoard.stopGame();
		threadControl.stopAll();
	}

	public void serverWon() {
		String messageServerWon = "Client won";
		playerStatusPanelClient.updateStatus(messageServerWon);
		playerStatusPanelServer.updateStatus(messageServerWon);
		playerStatusPanelClient.updatePairStatus(0, 0);
		playerStatusPanelServer.updatePairStatus(0, 0);
		gameBoard.stopGame();
		threadControl.stopAll();
	}

	public void disConnected() {
		String messageDisconnected = "Disconnected";
		playerStatusPanelClient.updateStatus(messageDisconnected);
		playerStatusPanelServer.updateStatus(messageDisconnected);
		gameBoard.stopGame();
		threadControl.stopAll();
	}

	public void startGame() {
		gameBoard.startGame();
	}

	public void makeGameBoard(int xSize, int ySize) {
		logger.fine("makeGameBoard x" + xSize + " y" + ySize);
		gameBoard.makeGameBoard(xSize, ySize);
	}
}
