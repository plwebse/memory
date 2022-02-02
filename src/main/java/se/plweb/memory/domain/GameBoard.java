package se.plweb.memory.domain;

import java.util.List;
import java.util.Map;

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

	boolean noOfPressedObjectIsCorrect();

	boolean isAMatch();

	void clearPressedObjects();

	void makeGameBoard(int xSize, int ySize);

	int getXSize();

	int getYSize();

	int getTotalSize();

	int getTotalNumberOfAttempts();

	void newEmptyGameBoard(int xSize, int ySize);

	List<Position> getPositions();

	Map<Position, Integer> createValuesForPositions();

}
