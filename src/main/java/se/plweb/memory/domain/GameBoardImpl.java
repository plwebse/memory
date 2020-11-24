package se.plweb.memory.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author Peter Lindblom
 */

public class GameBoardImpl implements GameBoard {

    private static final long serialVersionUID = 1L;
    private static final int pressedObjectsLength = 2;
    private static Logger logger;
    private int ySize;
    private int xSize;
    private int totalSize;
    private int totalNumberOfPairs;
    private int matchedPairs;
    private int totalNumberOfAttempts = 0;
    private List<GameObject> pressedObjects = Collections.synchronizedList(new ArrayList<>(pressedObjectsLength));
    private List<GameObject> gameBoard = Collections.synchronizedList(new ArrayList<>());

    public GameBoardImpl() {
        logger = Logger.getLogger(this.getClass().getName());

        for (int i = 0; i < pressedObjectsLength; i++) {
            pressedObjects.add(null);
        }
    }

    public void startGame() {

        this.randomizeGameObjectsValues();
        logger.fine("xsise=" + getXSize() + "ysize=" + getYSize());
        for (int x = 0; x < getXSize(); x++) {
            for (int y = 0; y < getYSize(); y++) {
                getGameObject(Position.create(x, y)).setState(
                        GameObjectState.NORMAL_STATE);
            }
        }
    }

    private void randomizeGameObjectsValues() {
        List<Integer> values = Collections.synchronizedList(new ArrayList<Integer>());

        values = addValuePairToList(1, getTotalNumberOfPairs());

        for (int x = 0; x < getXSize(); x++) {
            for (int y = 0; y < getYSize(); y++) {
                Position tmpPosition = Position.create(x, y);
                if (getGameObject(tmpPosition) instanceof GameObject) {
                    getGameObject(tmpPosition).setValue(
                            returnARandomValueFromAListAndThenRemoveIt(values));
                }
            }
        }
    }

    private List<Integer> addValuePairToList(Integer minValue, Integer maxValue) {
        List<Integer> values = Collections.synchronizedList(new ArrayList<Integer>());

        for (int currentValue = minValue; currentValue <= maxValue; currentValue++) {
            values.add(currentValue);
            values.add(currentValue);
        }

        return values;
    }

    private int returnARandomValueFromAListAndThenRemoveIt(List<Integer> list) {
        int temporaryRandomIndexNumber;
        int temporaryRandomValue;
        Random rnd = new Random();
        temporaryRandomIndexNumber = rnd.nextInt(list.size());
        temporaryRandomValue = list.get(temporaryRandomIndexNumber);
        list.remove(temporaryRandomIndexNumber);
        return temporaryRandomValue;
    }

    public void stopGame() {
        logger.fine("xsise=" + getXSize() + "ysize=" + getYSize());
        for (int x = 0; x < getXSize(); x++) {
            for (int y = 0; y < getYSize(); y++) {
                getGameObject(Position.create(x, y)).setState(
                        GameObjectState.PRESSED_STATE);
            }
        }
    }

    public synchronized void pressObject(GameObject obj) {
        logger.fine("pressObject:" + obj.toString());
        if (obj != null && obj.isInNormalState()) {
            for (int i = 0; i < getPressedObjectsLength(); i++) {
                if (getAPressedObject(i) == null) {
                    obj.setState(GameObjectState.PRESSED_STATE);
                    setAPressedObjects(i, obj);
                    break;
                }
            }
        }
    }

    public synchronized boolean isFull() {

        for (int i = 0; i < getPressedObjectsLength(); i++) {
            if (getAPressedObject(i) == null) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean isAMatch() {
        GameObject tmpGameObject = null;
        int tmpMatchedObjects = 0;

        for (int i = 0; i < getPressedObjectsLength(); i++) {
            if (i == 0 && (getAPressedObject(i) instanceof GameObject)) {
                tmpGameObject = getAPressedObject(i);
            } else if (i > 0
                    && (getAPressedObject(i) instanceof GameObject)
                    && tmpGameObject
                    .hasTheSameValueAndNotTheSameCoordinates(getAPressedObject(i))) {
                tmpMatchedObjects++;
            } else {
                // Finns inget att para ihop
                return false;
            }
        }

        if (getPressedObjectsLength() != (tmpMatchedObjects + 1)) {
            return false;
        }

        // färdig
        for (int i = 0; i < getPressedObjectsLength(); i++) {
            getAPressedObject(i).setState(GameObjectState.MATCHED_STATE);
            GameObject tmpGameObject2 = getGameObject(getAPressedObject(i)
                    .getPosition());
            tmpGameObject2.setState(GameObjectState.MATCHED_STATE);
            this.setGameObject(tmpGameObject2);
        }

        setMatchedPairs(getMatchedPairs() + 1);
        logger.fine("match");
        return true;
    }

    public synchronized void clearPressedObjects() {
        for (int i = 0; i < getPressedObjectsLength(); i++) {
            if (getAPressedObject(i) instanceof GameObject
                    && getAPressedObject(i).getState() == GameObjectState.PRESSED_STATE) {
                GameObject tmpGameObject2 = getGameObject(getAPressedObject(i)
                        .getPosition());
                tmpGameObject2.setState(GameObjectState.NORMAL_STATE);
                this.setGameObject(tmpGameObject2);
            }
            setAPressedObjects(i, null);
        }

        setTotalNumberOfAttempts(getTotalNumberOfAttempts() + 1);
    }

    public void newEmptyGameBoard(int xSize, int ySize) {
        setYSize(xSize);
        setXSize(ySize);
        setTotalSize(getXSize() * getYSize());

        gameBoard.clear();
        for (int i = 0; i < getTotalSize(); i++) {
            gameBoard.add(null);
        }

        setTotalNumberOfPairs(getTotalSize() / 2);
        setMatchedPairs(0);
    }

    public void makeGameBoard(int xSize, int ySize) {

        int value = 1;
        int i = 1;

        newEmptyGameBoard(xSize, ySize);

        createGameObjects(xSize, ySize)
				.forEach(this::setGameObject);
        /*
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {

                GameObject gameObject = new GameObjectImpl(value, Position.create(x, y));
                gameObject.setState(GameObjectState.PRESSED_STATE);

                this.setGameObject(gameObject);
                if (i % 2 == 0) {
                    value++;
                }
                i++;
            }
        }*/
    }

    public synchronized GameObject getGameObject(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        int pos = calculateListPosition(position.getXPos(), position.getYPos());

        return gameBoard.get(pos);
    }

    public synchronized void setGameObject(GameObject gameObject) {
        if (gameObject == null) {
            throw new IllegalArgumentException("Gameobject cannot be null");
        } else if (gameObject.getPosition() == null) {
            throw new IllegalArgumentException(
                    "Gameobjects Position cannot be null");
        }


        int pos = calculateListPosition(gameObject.getPosition().getXPos(), gameObject.getPosition().getYPos());

        gameBoard.set(pos, gameObject);

    }

    private int calculateListPosition(int x, int y) {
        return y * getXSize() + x;
    }

    public synchronized int getTotalNumberOfPairs() {
        return totalNumberOfPairs;
    }

    private synchronized void setTotalNumberOfPairs(int totalNumberOfPairs) {
        this.totalNumberOfPairs = totalNumberOfPairs;
    }

    public synchronized int getNumberOfMatchedPairs() {
        return matchedPairs;
    }

    public int getXSize() {
        return xSize;
    }

    private void setXSize(int xSize) {
        this.xSize = xSize;
    }

    public int getYSize() {
        return ySize;
    }

    private void setYSize(int ySize) {
        this.ySize = ySize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    private void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalNumberOfAttempts() {
        return totalNumberOfAttempts;
    }

    private synchronized void setTotalNumberOfAttempts(int value) {
        totalNumberOfAttempts = value;
    }

    private int getMatchedPairs() {
        return matchedPairs;
    }

    private synchronized void setMatchedPairs(int matchedPairs) {
        this.matchedPairs = matchedPairs;
    }

    private GameObject getAPressedObject(int index) {
        return pressedObjects.get(index);
    }

    private synchronized void setAPressedObjects(int index, GameObject pressedObject) {
        this.pressedObjects.set(index, pressedObject);
    }

    private int getPressedObjectsLength() {
        return pressedObjects.size();
    }

    private List<GameObject> createGameObjects(int xSize, int ySize) {
        List<GameObject> gameObjectList = new ArrayList<>();
        int value = 1;
        int i = 1;

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                gameObjectList.add(new GameObjectImpl(value, Position.create(x, y),
                        GameObjectState.PRESSED_STATE));
                if (i % 2 == 0) {
                    value++;
                }
                i++;
            }
        }

        return gameObjectList;
    }
}
