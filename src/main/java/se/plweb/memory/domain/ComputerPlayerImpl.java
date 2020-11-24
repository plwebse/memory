package se.plweb.memory.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Peter Lindblom
 */

public class ComputerPlayerImpl implements ComputerPlayer {

    private List<GameObject> visitedGameObjects = new ArrayList<>();
    private int lastX = 0;
    private int lastY = 0;
    private int numberOfPressedGameObjectsToRemember;

    public ComputerPlayerImpl(int numberOfPressedGameObjectsToRemember) {
        this.numberOfPressedGameObjectsToRemember = numberOfPressedGameObjectsToRemember;
    }

    public void makeAComputerMove(GameBoard gameBoard) {

        GameObject firstObjectToPress;
        GameObject secondObjectToPress;

        if (visitedGameObjects.size() > 0) {
            // Om det finns object som inte har blivit matchade använd de första
            // objekten
            firstObjectToPress = visitedGameObjects.get(0);
            gameBoard.pressObject(firstObjectToPress);
        } else {
            // Om det inte finns object som inte har blivit matchade hitta ett
            // nytt object
            firstObjectToPress = findAObjectToPress(gameBoard);
            addGameObjectToVisitedGameObjectsIfNotAlreadyThere(
                    firstObjectToPress, numberOfPressedGameObjectsToRemember);
            // press on firstObject
            gameBoard.pressObject(firstObjectToPress);
        }

        secondObjectToPress = getAVisitedGameObjectWithTheSameValue(firstObjectToPress);

        if (Objects.nonNull(secondObjectToPress)) {
            gameBoard.pressObject(secondObjectToPress);
        } else {
            secondObjectToPress = findAObjectToPress(gameBoard);
            addGameObjectToVisitedGameObjectsIfNotAlreadyThere(
                    secondObjectToPress, numberOfPressedGameObjectsToRemember);
            gameBoard.pressObject(secondObjectToPress);
        }

        checkIfThePressObjectsIsAMatchOrNotAndClearsThePressedObjects(
                gameBoard, firstObjectToPress, secondObjectToPress);
    }

    private GameObject findAObjectToPress(GameBoard gameBoard) {
        GameObject gameObjectToPress = null;
        while (gameObjectToPress == null) {
            if (lastX < (gameBoard.getXSize() - 1)) {
                lastX++;
            } else {
                lastX = 0;
                if (lastY < (gameBoard.getYSize() - 1)) {
                    lastY++;
                } else {
                    lastY = 0;
                }
            }

            Position tmpPosition = Position.create(lastX, lastY);

            if (gameBoard.getGameObject(tmpPosition) != null
                    && gameBoard.getGameObject(tmpPosition).isInNormalState()) {
                gameObjectToPress = gameBoard.getGameObject(tmpPosition);
                break;
            }
        }
        return gameObjectToPress;
    }

    private GameObject getAVisitedGameObjectWithTheSameValue(GameObject lookForGameObject) {

        return visitedGameObjects.stream()
                .filter(currentGameObject -> currentGameObject.hasTheSameValueAndNotTheSameCoordinates(lookForGameObject))
                .findFirst()
                .orElse(null);
    }

    private void addGameObjectToVisitedGameObjectsIfNotAlreadyThere(
            GameObject gameObject, int numberOfpressGameObjectsToRemember) {

        if (visitedGameObjects.size() > 0) {
            if (!visitedGameObjects.contains(gameObject)
                    && numberOfpressGameObjectsToRemember >= visitedGameObjects
                    .size()) {
                visitedGameObjects.add(gameObject);
            }
        } else if (visitedGameObjects.size() == 0) {
            visitedGameObjects.add(gameObject);
        }
    }

    private void checkIfThePressObjectsIsAMatchOrNotAndClearsThePressedObjects(
            GameBoard gameBoard, GameObject firstObjectToPress,
            GameObject secondObjectToPress) {
        if (gameBoard.isFull()) {
            if (gameBoard.isAMatch()) {

                if (visitedGameObjects.size() > 0
                        && visitedGameObjects.contains(firstObjectToPress)) {
                    visitedGameObjects.remove(firstObjectToPress);
                }
                if (visitedGameObjects.size() > 0
                        && visitedGameObjects.contains(secondObjectToPress)) {
                    visitedGameObjects.remove(secondObjectToPress);
                }
            }
            gameBoard.clearPressedObjects();
        }
    }

}
