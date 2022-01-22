package se.plweb.memory.gui;

import org.junit.Assert;
import org.junit.Test;
import se.plweb.memory.domain.Position;


public class GameObjectGuiImplTest {

    @Test()
    public void isPositionInsideOfGameObject() {
        Position position = Position.create(1, 1);
        GameObjectGuiImpl gameObjectGui = new GameObjectGuiImpl(1, position, null, 30, 30, 0, 0);

        Assert.assertTrue(gameObjectGui.isPositionInsideOfGameObject(position));

    }

    @Test()
    public void isPositionInsideOfGameObject2() {
        Position position = Position.create(31, 1);
        GameObjectGuiImpl gameObjectGui = new GameObjectGuiImpl(1, position, null, 30, 30, 30, 0);

        Assert.assertTrue(gameObjectGui.isPositionInsideOfGameObject(position));
    }
}