package se.plweb.memory.domain;

/**
 * @author Peter Lindblom
 * 
 */

public interface GameBoard {

	GameObject getGameObject(Position position);

	void setGameObject(GameObject gameObject);

	void stopGame();

	void startGame();

	int getTotalNumberOfPairs();

	int getNumberOfMatchedPairs();

	void pressObject(GameObject obj);

	boolean noPressedObjectIsCorrect();

	boolean isAMatch();

	void clearPressedObjects();

	void makeGameBoard(int xSize, int ySize);

	int getXSize();

	int getYSize();

	int getTotalSize();

	int getTotalNumberOfAttempts();

	void newEmptyGameBoard(int xSize, int ySize);

}
