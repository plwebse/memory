package se.plweb.memory.gui;

import java.util.logging.Logger;
import javax.swing.JPanel;
import se.plweb.memory.domain.*;

/**
 * 
 * @author Peter Lindblom
 */

public class GameSinglePlayer extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Logger logger;
	private GameBoardGui gameBoard = new GameBoardGui();
	private PlayerStatusPanel statusPanel = new PlayerStatusPanel(
			"Single player");
	private JPanel emptyStatusPanel = new JPanel();

	private ThreadControl threadControl = ThreadControl.getInstance();
	private Gui gui;

	public GameSinglePlayer(Gui gui, int xSize, int ySize) {
		logger = Logger.getLogger(this.getClass().getName());		
		this.gameBoard.makeGameBoard(xSize, ySize);
		this.gameBoard.startGame();
		this.gui = gui;

		CommonGameFunctions.applyGridBagConstraints(this, gameBoard,
				statusPanel, emptyStatusPanel);

		this.gui.startSinglePlayer(this);
	}

	public int getMatchedPairs() {
		logger.fine("getNumberOfMatchedPairs"
				+ gameBoard.getNumberOfMatchedPairs());
		return gameBoard.getNumberOfMatchedPairs();
	}

	public int getTotalNumberOfParis() {
		logger.fine("getNumberOfParis"
				+ gameBoard.getTotalNumberOfPairs());
		return gameBoard.getTotalNumberOfPairs();
	}

	public void updateStatusSinglePlayerStatus(int matchedPairs,
			int numberOfattempts) {
		statusPanel
				.updatePairStatus(matchedPairs, this.getTotalNumberOfParis());
		statusPanel.updateAttempts(numberOfattempts);
	}

	public int getNumberOfAttempts() {
		return gameBoard.getTotalNumberOfAttempts();
	}

	public void startGame() {		 
		this.gameBoard.startGame();
		boolean gotFocus = this.gameBoard.requestFocusInWindow();
		logger.fine("startGame: gotFocus:"+gotFocus);
	}

	public void singlePlayerWon() {
		statusPanel.updateStatus("You win");
		statusPanel.updatePairStatus(0, 0);
		gameBoard.stopGame();
		threadControl.stopAll();
	}
}