package se.plweb.memory.domain;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Peter Lindblom
 */

public class GameBoardImpl implements GameBoard {

    private static final int pressedObjectsLength = 2;
    private static Logger logger;
    private final List<GameObject> pressedObjects = Collections.synchronizedList(new ArrayList<>(pressedObjectsLength));
    private final List<GameObject> gameBoard = Collections.synchronizedList(new ArrayList<>());
    private int ySize;
    private int xSize;
    private int totalSize;
    private int totalNumberOfPairs;
    private int matchedPairs;
    private int totalNumberOfAttempts = 0;
    private final List<Position> positions = new ArrayList<>();

    public GameBoardImpl() {
        logger = Logger.getLogger(this.getClass().getName());
        pressedObjects.addAll(Collections.nCopies(pressedObjectsLength, null));
    }

    public void startGame() {
        this.randomizeGameObjectsValues();
        logger.fine("xSize=" + getXSize() + ",ySize=" + getYSize());

        getPositions()
                .forEach(position -> getGameObject(position).setState(GameObjectState.NORMAL_STATE));
    }

    private void randomizeGameObjectsValues() {
        List<Integer> values = addValuePairToList(getTotalNumberOfPairs());

        getPositions()
                .forEach(position -> getGameObject(position).setValue(
                        returnARandomValueFromAListAndThenRemoveIt(values)));
    }

    private List<Integer> addValuePairToList(Integer maxValue) {
        List<Integer> values = Collections.synchronizedList(new ArrayList<>());

        IntStream.rangeClosed(1, maxValue).forEach(value -> {
            values.add(value);
            values.add(value);
        });

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
        logger.fine("xSize=" + getXSize() + ",ySize=" + getYSize());
        getPositions().forEach(position -> getGameObject(position).setState(
                GameObjectState.PRESSED_STATE));

    }

    public synchronized void pressObject(GameObject obj) {
        logger.fine("pressObject:" + obj.toString());
        if (obj.isInNormalState()) {
            IntStream.range(0, getPressedObjectsLength())
                    .filter(index -> Objects.isNull(getAPressedObject(index)))
                    .findFirst()
                    .ifPresent(index -> {
                        obj.setState(GameObjectState.PRESSED_STATE);
                        setAPressedObjects(index, obj);
                    });
        }
    }

    public synchronized boolean noPressedObjectIsCorrect() { //TODO fix naming
        return pressedObjects.stream()
                .allMatch(Objects::nonNull) && pressedObjects.size() == pressedObjectsLength;
    }

    public synchronized boolean isAMatch() {
        Map<Position, Integer> positionValueMap = pressedObjects.stream()
                .collect(Collectors.toMap(GameObject::getPosition, GameObject::getValue));

        if (getDistinctCountEquals(positionValueMap.keySet(), getPressedObjectsLength())
                && getDistinctCountEquals(positionValueMap.values(), 1)) {
            IntStream.range(0, getPressedObjectsLength()).forEach(index -> {
                getAPressedObject(index).setState(GameObjectState.MATCHED_STATE);
                setGameObject(changeStateAndGetGameObject(getAPressedObject(index)
                        .getPosition(), GameObjectState.MATCHED_STATE));
            });

            setMatchedPairs(getMatchedPairs() + 1);
            logger.fine("match");
            return true;
        }
        logger.fine("no match");
        return false;
    }

    private boolean getDistinctCountEquals(Collection<?> collection, long expectedCount) {
        return expectedCount == Optional.ofNullable(collection)
                .map(c -> c.stream().distinct().count())
                .orElse(0L);
    }

    public synchronized void clearPressedObjects() {
        IntStream.range(0, getPressedObjectsLength()).forEach(index -> {
            if (getAPressedObject(index) != null
                    && getAPressedObject(index).getState() == GameObjectState.PRESSED_STATE) {
                setGameObject(changeStateAndGetGameObject(getAPressedObject(index)
                        .getPosition(), GameObjectState.NORMAL_STATE));
            }
            setAPressedObjects(index, null);
        });

        setTotalNumberOfAttempts(getTotalNumberOfAttempts() + 1);
    }

    public void newEmptyGameBoard(int xSize, int ySize) {
        setXSize(xSize);
        setYSize(ySize);
        setTotalSize(getXSize() * getYSize());
        setPositions();
        gameBoard.clear();
        gameBoard.addAll(Collections.nCopies(getTotalSize(), null));

        setTotalNumberOfPairs(getTotalSize() / 2);
        setMatchedPairs(0);
    }


    public void makeGameBoard(int xSize, int ySize) {

        newEmptyGameBoard(xSize, ySize);

        createGameObjects()
                .forEach(this::setGameObject);
    }

    private synchronized GameObject changeStateAndGetGameObject(Position position, GameObjectState gameObjectState) {
        GameObject gameObject = getGameObject(position);
        gameObject.setState(gameObjectState);
        return gameObject;
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
            throw new IllegalArgumentException("GameObject cannot be null");
        } else if (gameObject.getPosition() == null) {
            throw new IllegalArgumentException(
                    "GameObjects Position cannot be null");
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

    public synchronized int getTotalNumberOfAttempts() {
        return totalNumberOfAttempts;
    }

    private synchronized void setTotalNumberOfAttempts(int value) {
        totalNumberOfAttempts = value;
    }

    private synchronized int getMatchedPairs() {
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

    private List<GameObject> createGameObjects() {
        List<GameObject> gameObjectList = new ArrayList<>();
        int value = 1;
        int i = 1;

        for (Position position : getPositions()) {
            gameObjectList.add(GameObjectImpl.create(value, position,
                    GameObjectState.PRESSED_STATE));
            if (i % 2 == 0) {
                value++;
            }
            i++;
        }

        return gameObjectList;
    }

    private void setPositions() {
        positions.clear();
        for (int x = 0; x < this.getXSize(); x++) {
            for (int y = 0; y < this.getYSize(); y++) {
                positions.add(Position.create(x, y));
            }
        }
    }

    public List<Position> getPositions() {
        return positions;
    }
}
