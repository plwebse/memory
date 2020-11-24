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

	private GameBoardGui gameBoard = new GameBoardGui();
	private PlayerStatusPanel playerStatusPanelServer = new PlayerStatusPanel(
			"Server");
	private PlayerStatusPanel playerStatusPanelClient = new PlayerStatusPanel(
			"Client");
	private Gui gui;
	private ThreadControl threadControl = ThreadControl.getInstance();

	private String messageDisconnected = "Disconnected";
	private String messageWaitingForClient = "wating for client...";
	private String messageServerWon = "Client won";
	private String messageClientWon = "Server won";
	private String messageNotConnected = "Not connected...";

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
		playerStatusPanelClient.updateStatus(messageWaitingForClient);
		playerStatusPanelServer.updateStatus(messageWaitingForClient);
	}

	public void notConnected() {
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

	public void updateStatusClient(int matchedPairs, int numberOfattempts) {
		logger.fine("" + matchedPairs + " / "
				+ this.getTotalNumberOfParis());
		playerStatusPanelClient.updateStatus("");
		playerStatusPanelClient.updatePairStatus(matchedPairs, this
				.getTotalNumberOfParis());
		playerStatusPanelClient.updateAttempts(numberOfattempts);
	}

	public void updateStatusServer(int matchedPairs, int numberOfattempts) {
		logger.fine("" + matchedPairs + " / "
				+ this.getTotalNumberOfParis());
		playerStatusPanelServer.updateStatus("");
		playerStatusPanelServer.updatePairStatus(matchedPairs, this
				.getTotalNumberOfParis());
		playerStatusPanelServer.updateAttempts(numberOfattempts);
	}

	public void clientWon() {
		playerStatusPanelClient.updateStatus(messageClientWon);
		playerStatusPanelServer.updateStatus(messageClientWon);
		playerStatusPanelClient.updatePairStatus(0, 0);
		playerStatusPanelServer.updatePairStatus(0, 0);
		gameBoard.stopGame();
		threadControl.stopAll();
	}

	public void serverWon() {
		playerStatusPanelClient.updateStatus(messageServerWon);
		playerStatusPanelServer.updateStatus(messageServerWon);
		playerStatusPanelClient.updatePairStatus(0, 0);
		playerStatusPanelServer.updatePairStatus(0, 0);
		gameBoard.stopGame();
		threadControl.stopAll();
	}

	public void disConnected() {
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
