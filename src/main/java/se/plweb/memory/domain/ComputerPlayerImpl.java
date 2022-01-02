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
    private int lastIndex = 0;

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
            if (lastIndex >= gameBoard.getPositions().size()) {
                lastIndex = 0;
            }

            Position position = gameBoard.getPositions().get(lastIndex);
            gameObjectToPress = Optional.ofNullable(gameBoard.getGameObject(position))
                    .filter(GameObject::isInNormalState)
                    .orElse(null);

            lastIndex++;
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

        if (!visitedGameObjects.contains(gameObject)
                && numberOfPressedGameObjectsToRemember >= visitedGameObjects.size()) {
            visitedGameObjects.add(gameObject);
        }
    }

    private void checkIfThePressObjectsIsAMatchOrNotAndClearsThePressedObjects(
            GameBoard gameBoard, GameObject firstObjectToPress,
            GameObject secondObjectToPress) {
        if (gameBoard.noOfPressedObjectIsCorrect()) {
            if (gameBoard.isAMatch()) {
                visitedGameObjects.remove(firstObjectToPress);
                visitedGameObjects.remove(secondObjectToPress);
            }
            gameBoard.clearPressedObjects();
        }
    }

}
