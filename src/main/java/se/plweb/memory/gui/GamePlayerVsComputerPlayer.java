package se.plweb.memory.gui;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

import se.plweb.memory.domain.*;

/**
 * @author Peter Lindblom
 */

public class GamePlayerVsComputerPlayer extends JPanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger;
    private final GameBoardGuiPlayerVsComputerPlayer gameBoard;
    private final GameBoard computerPlayersGameBoard = new GameBoardImpl();
    private final PlayerStatusPanel humanPlayerStatusPanel = new PlayerStatusPanel(
            "Human player");
    private final PlayerStatusPanel computerPlayerStatusPanel = new PlayerStatusPanel(
            "Computer player");
    private final ThreadControl threadControl = ThreadControl.getInstance();

    public GamePlayerVsComputerPlayer(Gui gui, int xSize, int ySize,
                                      ComputerPlayers difficulty) {
        logger = Logger.getLogger(this.getClass().getName());
        computerPlayersGameBoard.makeGameBoard(xSize, ySize);
        computerPlayersGameBoard.startGame();
        ComputerPlayer computerPlayer = difficulty
                .createComputerPlayer(computerPlayersGameBoard.getTotalSize());
        gameBoard = new GameBoardGuiPlayerVsComputerPlayer(computerPlayer,
                computerPlayersGameBoard);
        gameBoard.makeGameBoard(xSize, ySize);
        gameBoard.startGame();

        CommonGameFunctions.applyGridBagConstraints(this, gameBoard,
                humanPlayerStatusPanel, computerPlayerStatusPanel);

        gui.startPlayerVsComputerPlayer(this);
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
        logger.log(Level.FINE, "getComputerPlayersNumberOfAttempts:"
                + computerPlayersGameBoard.getTotalNumberOfAttempts());
        return computerPlayersGameBoard.getTotalNumberOfAttempts();
    }

    public void updateHumanPlayerStatus(int matchedPairs, int numberOfAttempts) {
        humanPlayerStatusPanel.updatePairStatus(matchedPairs, this
                .getTotalNumberOfParis());
        humanPlayerStatusPanel.updateAttempts(numberOfAttempts);
    }

    public void updateComputerPlayerStatus(int matchedPairs,
                                           int numberOfAttempts) {
        computerPlayerStatusPanel.updatePairStatus(matchedPairs, this
                .getTotalNumberOfParis());
        computerPlayerStatusPanel.updateAttempts(numberOfAttempts);
    }

    public void computerPlayerWon() {
        String messageComputerPlayerWon = "Computer player won";
        for (PlayerStatusPanel playerStatusPanel : getPlayerStatusPanels()) {
            playerStatusPanel.updateStatus(messageComputerPlayerWon);
            playerStatusPanel.updatePairStatus(0, 0);
        }

        gameBoard.stopGame();
        computerPlayersGameBoard.stopGame();
        threadControl.stopAll();
    }

    public void humanPlayerWon() {
        String messageHumanPlayerWon = "Human player won";
        for (PlayerStatusPanel playerStatusPanel : getPlayerStatusPanels()) {
            playerStatusPanel.updateStatus(messageHumanPlayerWon);
            playerStatusPanel.updatePairStatus(0, 0);
        }

        gameBoard.stopGame();
        computerPlayersGameBoard.stopGame();
        threadControl.stopAll();
    }

    private List<PlayerStatusPanel> getPlayerStatusPanels() {
        return Arrays.asList(computerPlayerStatusPanel, humanPlayerStatusPanel);
    }
}