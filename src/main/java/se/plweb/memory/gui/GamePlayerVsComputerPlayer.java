package se.plweb.memory.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import se.plweb.memory.domain.*;

/**
 * 
 * @author Peter Lindblom
 */

public class GamePlayerVsComputerPlayer extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Logger logger;
	private GameBoardGuiPlayerVsComputerPlayer gameBoard;
	private GameBoard computerPlayersGameBoard = new GameBoardImpl();
	private ComputerPlayer computerPlayer = null;
	private PlayerStatusPanel humanPlayerStatusPanel = new PlayerStatusPanel(
			"Human player");
	private PlayerStatusPanel computerPlayerStatusPanel = new PlayerStatusPanel(
			"Computer player");
	private ThreadControl threadControl = ThreadControl.getInstance();
	private Gui gui;
	private String messageHumanPlayerWon = "Human player won";
	private String messageComputerPlayerWon = "Computer player won";

	public GamePlayerVsComputerPlayer(Gui gui, int xSize, int ySize,
			ComputerPlayers difficulty) {
		logger = Logger.getLogger(this.getClass().getName());
		computerPlayersGameBoard.makeGameBoard(xSize, ySize);
		computerPlayersGameBoard.startGame();
		computerPlayer = difficulty
				.createComputerPlayer(computerPlayersGameBoard.getTotalSize());
		gameBoard = new GameBoardGuiPlayerVsComputerPlayer(computerPlayer,
				computerPlayersGameBoard);
		gameBoard.makeGameBoard(xSize, ySize);
		gameBoard.startGame();
		this.gui = gui;

		CommonGameFunctions.applyGridBagConstraints(this, gameBoard,
				humanPlayerStatusPanel, computerPlayerStatusPanel);

		this.gui.startPlayerVsComputerPlayer(this);
	}

	public int getHumanPlayersMatchedPairs() {
		return gameBoard.getNumberOfMatchedPairs();
	}

	public int getComputerPlayersMatchedPairs() {
		return computerPlayersGameBoard.getNumberOfMatchedPairs();
	}

	public int getTotalNumberOfParis() {
		return gameBoard.getTotalNumberOfPairs();
	}

	public int getHumanPlayersNumberOfAttempts() {
		return gameBoard.getTotalNumberOfAttempts();
	}

	public int getComputerPlayersNumberOfAttempts() {
		logger.log(Level.FINE, "getComputerPlayersNumberOfAttempts="
				+ computerPlayersGameBoard.getTotalNumberOfAttempts());
		return computerPlayersGameBoard.getTotalNumberOfAttempts();
	}

	public void updateHumanPlayerStatus(int matchedPairs, int numberOfattempts) {
		humanPlayerStatusPanel.updatePairStatus(matchedPairs, this
				.getTotalNumberOfParis());
		humanPlayerStatusPanel.updateAttempts(numberOfattempts);
	}

	public void updateComputerPlayerStatus(int matchedPairs,
			int numberOfattempts) {
		computerPlayerStatusPanel.updatePairStatus(matchedPairs, this
				.getTotalNumberOfParis());
		computerPlayerStatusPanel.updateAttempts(numberOfattempts);
	}

	public void startGame() {
		this.gameBoard.startGame();
	}

	public void computerPlayerWon() {
		computerPlayerStatusPanel.updateStatus(messageComputerPlayerWon);
		humanPlayerStatusPanel.updateStatus(messageComputerPlayerWon);
		humanPlayerStatusPanel.updatePairStatus(0, 0);
		computerPlayerStatusPanel.updatePairStatus(0, 0);
		gameBoard.stopGame();
		computerPlayersGameBoard.stopGame();
		threadControl.stopAll();
	}

	public void humanPlayerWon() {
		computerPlayerStatusPanel.updateStatus(messageHumanPlayerWon);
		humanPlayerStatusPanel.updateStatus(messageHumanPlayerWon);
		humanPlayerStatusPanel.updatePairStatus(0, 0);
		computerPlayerStatusPanel.updatePairStatus(0, 0);
		gameBoard.stopGame();
		computerPlayersGameBoard.stopGame();
		threadControl.stopAll();
	}
}