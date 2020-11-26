package se.plweb.memory.domain;

/**
 * @author Peter Lindblom
 */

public class GameObjectImpl implements GameObject {

    private int value;
    private final Position position;
    private GameObjectState state;

    private GameObjectImpl(int value, Position position, GameObjectState gameObjectState) {
        this.value = value;
        this.position = position;
        setState(gameObjectState);
    }

    public static GameObject create(int value, Position position) {
        return create(value, position, GameObjectState.NORMAL_STATE);
    }

    public static GameObject create(int value, Position position, GameObjectState gameObjectState) {
        return new GameObjectImpl(value, position, gameObjectState);
    }

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }

    public synchronized GameObjectState getState() {
        return state;
    }

    public synchronized void setState(GameObjectState value) {
        state = value;
    }

    public synchronized boolean isInNormalState() {
        return GameObjectState.NORMAL_STATE.equals(getState());
    }

    public synchronized boolean hasTheSameValueAndNotTheSameCoordinates(GameObject gameObject) {
        return gameObject.getValue() == this.getValue()
                && !this.position.equals(gameObject.getPosition());
    }

    public synchronized boolean hasTheSameValueAndTheSameCoordinates(GameObject gameObject) {
        return gameObject.getValue() == this.getValue()
                && gameObject.getPosition().equals(this.position);
    }

    @Override
    public String toString() {
        return "GameObjectImpl value=" + getValue() + ", state=" + getState()
                + getPosition().toString();
    }

    @Override
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GameObject)) {
            return false;
        } else {
            return hasTheSameValueAndTheSameCoordinates((GameObject) obj);
        }
    }

    @Override
    public int hashCode() {
        int tmpHash = 0;
        tmpHash += position.hashCode();
        tmpHash += getState().hashCode();
        tmpHash += getValue();
        return tmpHash;
    }

    public synchronized Position getPosition() {
        return Position.clone(position);
    }
}
