package se.plweb.memory.domain;

/**
 * @author Peter Lindblom
 */

public interface GameObject {

    public GameObjectState getState();

    public void setState(GameObjectState state);

    public int getValue();

    public void setValue(int value);

    public Position getPosition();

    public void setPosition(Position position);

    public boolean isInNormalState();

    public boolean hasTheSameValueAndTheSameCoordinates(GameObject gameObject);

    public boolean hasTheSameValueAndNotTheSameCoordinates(GameObject gameObject);

}
