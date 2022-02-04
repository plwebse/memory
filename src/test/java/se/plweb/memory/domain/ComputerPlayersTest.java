package se.plweb.memory.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Lindblom
 */
public class ComputerPlayersTest {

    GameBoard gameBoard = new GameBoardImpl();

    @Before
    public void setUp() {
        gameBoard = new GameBoardImpl();
        gameBoard.makeGameBoard(10, 10);
        gameBoard.startGame();
    }

    @After
    public void tearDown() {
        gameBoard.stopGame();
        gameBoard = null;
    }

    @Test
    public void totalNumberOfPairsIsGreaterThanZero() {

        boolean actual = gameBoard.getTotalNumberOfPairs() > 0;

        Assert.assertTrue(actual);
    }

    @Test
    public void getNumberOfMatchedPairsShouldBeZero() {

        boolean actual = gameBoard.getNumberOfMatchedPairs() == 0;

        Assert.assertTrue(actual);
    }

    @Test
    public void nrOfComputerPlayers() {
        ComputerPlayers[] computerPlayers = ComputerPlayers.values();

        for (ComputerPlayers computerPlayer : computerPlayers) {
            System.out.println(computerPlayer.getDescription());
        }

        Assert.assertTrue(computerPlayers.length > 0);
    }

    @Test
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

        Assert.assertTrue(actual);
    }

    @Test
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

        Assert.assertTrue(actual);
    }

}
