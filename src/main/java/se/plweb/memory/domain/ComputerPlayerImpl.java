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
        List<GameObject> gameObjectsToPress = new ArrayList<>(2);

        gameObjectsToPress.add(
                visitedGameObjects.stream().findFirst()
                        .orElseGet(() -> findAObjectToPress(gameBoard)));

        gameObjectsToPress.add(
                getAVisitedGameObjectWithTheSameValue(gameObjectsToPress.get(0))
                        .orElseGet(() -> findAObjectToPress(gameBoard))
        );

        gameObjectsToPress.forEach(
                gameObject ->
                        addGameObjectToVisitedGameObjectsIfNotAlreadyThere(gameObject, numberOfPressedGameObjectsToRemember)
        );

        gameObjectsToPress.forEach(gameBoard::pressObject);

        checkIfThePressObjectsIsAMatchOrNotAndClearsThePressedObjects(
                gameBoard, gameObjectsToPress
        );
    }

    private GameObject findAObjectToPress(GameBoard gameBoard) {

        Optional<GameObject> optionalGameObjectToPress;

        do {
            if (lastIndex >= gameBoard.getPositions().size()) {
                lastIndex = 0;
            }

            optionalGameObjectToPress = getGameObjectInNormalStateOnGameBoardAtPosition(
                    gameBoard,
                    gameBoard.getPositions().get(lastIndex)
            );

            lastIndex++;
        } while (!optionalGameObjectToPress.isPresent());

        return optionalGameObjectToPress.get();
    }

    private Optional<GameObject> getGameObjectInNormalStateOnGameBoardAtPosition(GameBoard gameBoard, Position position) {
        return Optional.ofNullable(gameBoard.getGameObject(position))
                .filter(GameObject::isInNormalState);
    }

    private Optional<GameObject> getAVisitedGameObjectWithTheSameValue(GameObject lookForGameObject) {
        return visitedGameObjects.stream()
                .filter(currentGameObject -> currentGameObject.hasTheSameValueAndNotTheSameCoordinates(lookForGameObject))
                .findFirst();
    }

    private void addGameObjectToVisitedGameObjectsIfNotAlreadyThere(
            GameObject gameObject, int numberOfPressedGameObjectsToRemember) {

        if (!visitedGameObjects.contains(gameObject)
                && numberOfPressedGameObjectsToRemember >= visitedGameObjects.size()) {
            visitedGameObjects.add(gameObject);
        }
    }

    private void checkIfThePressObjectsIsAMatchOrNotAndClearsThePressedObjects(
            GameBoard gameBoard, List<GameObject> gameObjectsToPress) {
        if (gameBoard.noOfPressedObjectIsCorrect()) {
            if (gameBoard.isAMatch()) {
                visitedGameObjects.remove(gameObjectsToPress.get(0));
                visitedGameObjects.remove(gameObjectsToPress.get(1));
            }
            gameBoard.clearPressedObjects();
        }
    }
}
