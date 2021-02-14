package se.plweb.memory.domain;

import junit.framework.TestCase;

import java.util.stream.Collectors;

/**
 * @author Peter Lindblom
 */
public class GameBoardImplTest extends TestCase {
    final GameBoard gameBoard = new GameBoardImpl();

    protected void setUp() {
        gameBoard.makeGameBoard(10, 10);
    }

    public void testSetGameBoard() {
        assertNotNull(gameBoard.getGameObject(Position.create(0, 0)));
    }

    public void testSetGameObject() {
        assertNotNull(gameBoard.getGameObject(Position.create(9, 9)));
    }

    public void testStartGame() {
        gameBoard.startGame();
        boolean actual = gameBoard.getPositions().stream()
                .allMatch(position -> (gameBoard.getGameObject(position).getState() == GameObjectState.NORMAL_STATE));
        gameBoard.stopGame();
        assertTrue(actual);
    }

    public void testPressObject() {
        gameBoard.startGame();

        int actual = gameBoard.getPositions().stream().filter(position -> {
            gameBoard.pressObject(gameBoard.getGameObject(position));
            return (gameBoard.getGameObject(position).getState() == GameObjectState.PRESSED_STATE);
        }).collect(Collectors.toList()).size();

        assertTrue(gameBoard.noPressedObjectIsCorrect());
        assertEquals(2, actual);

        gameBoard.stopGame();
    }

    public void testIsFull() {

        gameBoard.startGame();

        gameBoard.pressObject(gameBoard.getGameObject(Position.create(
                0, 0)));
        gameBoard.pressObject(gameBoard.getGameObject(Position.create(
                0, 1)));
        boolean actual = gameBoard.noPressedObjectIsCorrect();

        gameBoard.stopGame();

        assertTrue(actual);
    }

    public void testIsAMatchAndClearPressedObjects() {
        boolean actual = false;

        gameBoard.startGame();

        ComputerPlayer cp = ComputerPlayers.EASY.createComputerPlayer(gameBoard
                .getTotalSize());

        while (gameBoard.getNumberOfMatchedPairs() != gameBoard
                .getTotalNumberOfPairs()) {

            cp.makeAComputerMove(gameBoard);
            if (gameBoard.getNumberOfMatchedPairs() == 2) {
                actual = true;
                break;
            }
        }

        gameBoard.stopGame();
        System.out.println(gameBoard.getTotalNumberOfAttempts());
        assertTrue(actual);
    }

    public void testNumberOfAttemptsAndMatchedPairs() {
        boolean actual = false;

        gameBoard.startGame();

        ComputerPlayer cp = ComputerPlayers.EASY.createComputerPlayer(gameBoard
                .getTotalSize());

        while (gameBoard.getNumberOfMatchedPairs() != gameBoard
                .getTotalNumberOfPairs()) {

            cp.makeAComputerMove(gameBoard);
            if (gameBoard.getNumberOfMatchedPairs() > 0
                    && gameBoard.getTotalNumberOfAttempts() > 0) {
                actual = true;
                break;
            }
        }

        gameBoard.stopGame();
        System.out.println(gameBoard.getTotalNumberOfAttempts());
        assertTrue(actual);
    }

    public void testToSolveGameWithComputerPlayerMove() {
        boolean actual = false;

        gameBoard.startGame();

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

        gameBoard.stopGame();
        System.out.println(gameBoard.getTotalNumberOfAttempts());
        assertTrue(actual);
    }

    public void testStopGame() {

        gameBoard.startGame();
        gameBoard.stopGame();
        boolean actual = gameBoard.getPositions().stream()
                .allMatch(position -> gameBoard.getGameObject(position).getState() == GameObjectState.PRESSED_STATE);

        assertTrue(actual);
    }
}
