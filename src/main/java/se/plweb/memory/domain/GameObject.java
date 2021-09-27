package se.plweb.memory.domain;

/**
 * @author Peter Lindblom
 */

public interface GameObject {

    GameObjectState getState();

    void setState(GameObjectState state);

    int getValue();

    void setValue(int value);

    Position getPosition();

    boolean isInNormalState();

    boolean isInDisabledState();

    boolean hasTheSameValueAndTheSameCoordinates(GameObject gameObject);

    boolean hasTheSameValueAndNotTheSameCoordinates(GameObject gameObject);

}
