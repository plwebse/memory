package se.plweb.memory.domain;

import junit.framework.TestCase;

import java.util.*;
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

        long actual = gameBoard.getPositions().stream().filter(position -> {
            gameBoard.pressObject(gameBoard.getGameObject(position));
            return (gameBoard.getGameObject(position).getState() == GameObjectState.PRESSED_STATE);
        }).count();

        assertTrue(gameBoard.noOfPressedObjectIsCorrect());
        assertEquals(2L, actual);

        gameBoard.stopGame();
    }

    public void testIsFull() {

        gameBoard.startGame();

        gameBoard.pressObject(gameBoard.getGameObject(Position.create(0, 0)));
        gameBoard.pressObject(gameBoard.getGameObject(Position.create(0, 1)));
        boolean actual = gameBoard.noOfPressedObjectIsCorrect();

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

        int counter = 0;

        for (ComputerPlayers computerPlayer : ComputerPlayers.values()) {
            gameBoard.makeGameBoard(10, 10);
            gameBoard.startGame();

            ComputerPlayer cp = computerPlayer.createComputerPlayer(gameBoard
                    .getTotalSize());

            while (gameBoard.getNumberOfMatchedPairs() != gameBoard.getTotalNumberOfPairs()) {
                cp.makeAComputerMove(gameBoard);
                if (gameBoard.getNumberOfMatchedPairs() == gameBoard
                        .getTotalNumberOfPairs()) {
                    counter++;
                    break;
                }
            }

            gameBoard.stopGame();
            System.out.println(gameBoard.getTotalNumberOfAttempts());
        }
        assertEquals(ComputerPlayers.values().length, counter);
    }

    public void testStopGame() {

        gameBoard.startGame();
        gameBoard.stopGame();
        boolean actual = gameBoard.getPositions().stream()
                .allMatch(position -> gameBoard.getGameObject(position).getState() == GameObjectState.PRESSED_STATE);

        assertTrue(actual);
    }

    public void testCreateValuesForPositions() {

        Map<Position, Integer> positionValueMap = gameBoard.createValuesForPositions();

        assertEquals(100, positionValueMap.values().size());

        Set<Integer> uniqueValues = new HashSet<>(positionValueMap.values());

        assertEquals(50, uniqueValues.size());

        assertEquals(Optional.of(50), positionValueMap.values().stream().max(Integer::compare));
        assertEquals(Optional.of(1), positionValueMap.values().stream().min(Integer::compare));




    }
}
