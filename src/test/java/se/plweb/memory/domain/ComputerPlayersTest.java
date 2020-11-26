package se.plweb.memory.domain;

import junit.framework.TestCase;

/**
 * @author Peter Lindblom
 */
public class ComputerPlayersTest extends TestCase {

    GameBoard gameBoard = new GameBoardImpl();

    @Override
    protected void setUp() {
        gameBoard = new GameBoardImpl();
        gameBoard.makeGameBoard(10, 10);
        gameBoard.startGame();
    }

    @Override
    protected void tearDown() {
        gameBoard.stopGame();
        gameBoard = null;
    }

    public void testThisSetUp() {

        boolean actual = false;

        if (gameBoard.getTotalNumberOfPairs() > 0) {
            actual = true;
        }

        assertTrue(actual);
    }

    public void testThisSetUp2() {

        boolean actual = false;

        if (gameBoard.getNumberOfMatchedPairs() == 0) {
            actual = true;
        }

        assertTrue(actual);
    }

    public void testEnumFactory2() {

        boolean actual = false;

        ComputerPlayers[] computerPlayers = ComputerPlayers.values();

        for (ComputerPlayers computerPlayer : computerPlayers) {
            System.out.println(computerPlayer.getDescription());
        }

        if (computerPlayers.length > 0) {
            actual = true;
        }

        assertTrue(actual);

    }

    public void testToSolveGameWithComputerPlayerMoveEasy() {
        boolean actual = false;

        ComputerPlayer cp = ComputerPlayers.EASY.createComputerPlayer(gameBoard
                .getTotalSize());

        while (gameBoard.getNumberOfMatchedPairs() != gameBoard
                .getTotalNumberOfPairs()) {
            cp.makeAComputerMove(gameBoard);
            if (gameBoard.getNumberOfMatchedPairs() == gameBoard
                    .getTotalNumberOfPairs()) {
                actual = true;
                break;
            }
        }

        System.out.println("ComputerPlayerEasy()"
                + gameBoard.getTotalNumberOfAttempts());

        assertTrue(actual);
    }

    public void testToSolveGameWithComputerPlayerMoveAdvanced() {
        boolean actual = false;

        ComputerPlayer cp = ComputerPlayers.HARD.createComputerPlayer(gameBoard
                .getTotalSize());

        while (gameBoard.getNumberOfMatchedPairs() != gameBoard
                .getTotalNumberOfPairs()) {
            cp.makeAComputerMove(gameBoard);
            if (gameBoard.getNumberOfMatchedPairs() == gameBoard
                    .getTotalNumberOfPairs()) {
                actual = true;
                break;
            }
        }

        System.out.println("ComputerPlayerAdvanced()"
                + gameBoard.getTotalNumberOfAttempts());

        assertTrue(actual);
    }

}
