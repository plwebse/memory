package se.plweb.memory.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Peter Lindblom
 */

public class ComputerPlayerImpl implements ComputerPlayer {

    private final List<GameObject> visitedGameObjects = new ArrayList<>();
    private final int numberOfPressedGameObjectsToRemember;
    private int lastX = 0;
    private int lastY = 0;

    public ComputerPlayerImpl(int numberOfPressedGameObjectsToRemember) {
        this.numberOfPressedGameObjectsToRemember = numberOfPressedGameObjectsToRemember;
    }

    public void makeAComputerMove(GameBoard gameBoard) {

        GameObject firstObjectToPress = visitedGameObjects.stream().findFirst()
                .orElseGet(() -> findAObjectToPress(gameBoard));

        GameObject secondObjectToPress = Optional.ofNullable(getAVisitedGameObjectWithTheSameValue(firstObjectToPress))
                .orElseGet(() -> findAObjectToPress(gameBoard));

        addGameObjectToVisitedGameObjectsIfNotAlreadyThere(firstObjectToPress, numberOfPressedGameObjectsToRemember);
        addGameObjectToVisitedGameObjectsIfNotAlreadyThere(secondObjectToPress, numberOfPressedGameObjectsToRemember);

        gameBoard.pressObject(firstObjectToPress);
        gameBoard.pressObject(secondObjectToPress);

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

            Position position = Position.create(lastX, lastY);

            gameObjectToPress = Optional.ofNullable(gameBoard.getGameObject(position))
                    .filter(GameObject::isInNormalState)
                    .orElse(null);

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
            GameObject gameObject, int numberOfPressedGameObjectsToRemember) {

        if(!visitedGameObjects.contains(gameObject)
                && numberOfPressedGameObjectsToRemember >= visitedGameObjects.size()){
            visitedGameObjects.add(gameObject);
        }
    }

    private void checkIfThePressObjectsIsAMatchOrNotAndClearsThePressedObjects(
            GameBoard gameBoard, GameObject firstObjectToPress,
            GameObject secondObjectToPress) {
        if (gameBoard.noPressedObjectIsCorrect()) {
            if (gameBoard.isAMatch()) {
                visitedGameObjects.remove(firstObjectToPress);
                visitedGameObjects.remove(secondObjectToPress);
            }
            gameBoard.clearPressedObjects();
        }
    }

}
