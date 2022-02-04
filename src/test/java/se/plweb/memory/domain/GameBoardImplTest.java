package se.plweb.memory.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author Peter Lindblom
 */
public class GameBoardImplTest {
    final GameBoard gameBoard = new GameBoardImpl();

    @Before
    public void setUp() {
        gameBoard.makeGameBoard(10, 10);
    }

    @Test
    public void setGameBoard() {
        Assert.assertNotNull(gameBoard.getGameObject(Position.create(0, 0)));
    }

    @Test
    public void setGameObject() {
        Assert.assertNotNull(gameBoard.getGameObject(Position.create(9, 9)));
    }

    @Test
    public void startGame() {
        gameBoard.startGame();
        boolean actual = gameBoard.getPositions().stream()
                .allMatch(position -> (gameBoard.getGameObject(position).getState() == GameObjectState.NORMAL_STATE));
        gameBoard.stopGame();
        Assert.assertTrue(actual);
    }

    @Test
    public void pressObject() {
        gameBoard.startGame();

        long actual = gameBoard.getPositions().stream().filter(position -> {
            gameBoard.pressObject(gameBoard.getGameObject(position));
            return (gameBoard.getGameObject(position).getState() == GameObjectState.PRESSED_STATE);
        }).count();

        Assert.assertTrue(gameBoard.noOfPressedObjectIsCorrect());
        Assert.assertEquals(2L, actual);

        gameBoard.stopGame();
    }

    @Test
    public void isFull() {

        gameBoard.startGame();

        gameBoard.pressObject(gameBoard.getGameObject(Position.create(0, 0)));
        gameBoard.pressObject(gameBoard.getGameObject(Position.create(0, 1)));
        boolean actual = gameBoard.noOfPressedObjectIsCorrect();

        gameBoard.stopGame();

        Assert.assertTrue(actual);
    }

    @Test
    public void isAMatchAndClearPressedObjects() {
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
        Assert.assertTrue(actual);
    }

    @Test
    public void numberOfAttemptsAndMatchedPairs() {
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
        Assert.assertTrue(actual);
    }

    @Test
    public void solveGameWithComputerPlayerMove() {

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
        Assert.assertEquals(ComputerPlayers.values().length, counter);
    }

    @Test
    public void stopGame() {

        gameBoard.startGame();
        gameBoard.stopGame();
        boolean actual = gameBoard.getPositions().stream()
                .allMatch(position -> gameBoard.getGameObject(position).getState() == GameObjectState.PRESSED_STATE);

        Assert.assertTrue(actual);
    }

    @Test
    public void createValuesForPositions() {

        Map<Position, Integer> positionValueMap = gameBoard.createValuesForPositions();

        Assert.assertEquals(100, positionValueMap.values().size());

        Set<Integer> uniqueValues = new HashSet<>(positionValueMap.values());

        Assert.assertEquals(50, uniqueValues.size());

        Assert.assertEquals(Optional.of(50), positionValueMap.values().stream().max(Integer::compare));
        Assert.assertEquals(Optional.of(1), positionValueMap.values().stream().min(Integer::compare));

    }
}
