package se.plweb.memory.domain;

import junit.framework.TestCase;

/**
 * @author Peter Lindblom
 * 
 */
public class GameBoardImplTest extends TestCase {
	GameBoard gameBoard = new GameBoardImpl();

	protected void setUp() throws Exception {
		gameBoard.makeGameBoard(10, 10);
	}

	public void testSetGameBoard() {

		assertNotNull(gameBoard.getGameObject(Position.create(0, 0)));
	}

	public void testSetGameObject() {
		assertNotNull(gameBoard.getGameObject(Position.create(9, 9)));
	}

	public void testStartGame() {
		boolean expected = true;
		boolean actual = true;

		gameBoard.startGame();
		x: for (int x = 0; x < gameBoard.getXSize(); x++) {
			for (int y = 0; y < gameBoard.getYSize(); y++) {
				Position tmpPosition = Position.create(x, y);
				if (gameBoard.getGameObject(tmpPosition) != null
						&& gameBoard.getGameObject(tmpPosition).getState() != GameObjectState.NORMAL_STATE) {
					actual = false;
					break x;
				}
			}
		}

		gameBoard.stopGame();

		assertEquals(expected, actual);
	}

	public void testPressObject() {
		int expected = 2;
		int actual = 0;

		gameBoard.startGame();

		x: for (int x = 0; x < gameBoard.getXSize(); x++) {
			for (int y = 0; y < gameBoard.getYSize(); y++) {
				Position tmpPosition = Position.create(x, y);

				gameBoard.pressObject(gameBoard.getGameObject(tmpPosition));
				if (gameBoard.getGameObject(tmpPosition).getState() == GameObjectState.PRESSED_STATE) {
					actual++;
				}
				if (gameBoard.isFull()) {
					break x;
				}
			}
		}

		gameBoard.stopGame();

		assertEquals(expected, actual);
	}

	public void testIsFull() {
		boolean expected = true;
		boolean actual = true;

		gameBoard.startGame();

		gameBoard.pressObject(gameBoard.getGameObject(Position.create(
				0, 0)));
		gameBoard.pressObject(gameBoard.getGameObject(Position.create(
				0, 1)));
		actual = gameBoard.isFull();

		gameBoard.stopGame();

		assertEquals(expected, actual);
	}

	public void testIsAMatchAndClearPressedObjects() {
		boolean expected = true;
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
		assertEquals(expected, actual);
	}

	public void testNumberOfAttemptsAndMatchedPairs() {
		boolean expected = true;
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
		assertEquals(expected, actual);
	}

	public void testToSolveGameWithComputerPlayerMove() {
		boolean expected = true;
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
		assertEquals(expected, actual);
	}

	public void testStopGame() {
		boolean expected = true;
		boolean actual = true;
		gameBoard.startGame();
		gameBoard.stopGame();
		x: for (int x = 0; x < gameBoard.getXSize(); x++) {
			for (int y = 0; y < gameBoard.getYSize(); y++) {
				Position tmpPosition = Position.create(x, y);
				if (gameBoard.getGameObject(tmpPosition) != null
						&& gameBoard.getGameObject(tmpPosition).getState() != GameObjectState.PRESSED_STATE) {
					actual = false;
					break x;
				}
			}
		}
		assertEquals(expected, actual);
	}
}
