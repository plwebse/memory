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

    void setPosition(Position position);

    boolean isInNormalState();

    boolean hasTheSameValueAndTheSameCoordinates(GameObject gameObject);

    boolean hasTheSameValueAndNotTheSameCoordinates(GameObject gameObject);

}
