package se.plweb.memory.gui;

import java.util.logging.Logger;
import se.plweb.memory.domain.*;

/**
 * 
 * @author Peter Lindblom
 */

public class GameBoardGuiPlayerVsComputerPlayer extends GameBoardGui {

	private static final long serialVersionUID = 1L;
	private static Logger logger;
	private ComputerPlayer computerPlayer;
	private GameBoard computerPlayersGameBoard;

	public GameBoardGuiPlayerVsComputerPlayer(ComputerPlayer computerPlayer,
			GameBoard computerPlayersGameBoard) {
		super();
		this.computerPlayersGameBoard = computerPlayersGameBoard;
		this.computerPlayer = computerPlayer;
		logger = Logger.getLogger(this.getClass().getName());

	}
	
	@Override
	protected synchronized void doAfterTimerEvent() {
		logger.fine("doAfterTimerEvent()");
		computerPlayer.makeAComputerMove(computerPlayersGameBoard);
		repaint();
	}
}
