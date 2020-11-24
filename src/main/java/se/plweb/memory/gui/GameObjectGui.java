package se.plweb.memory.gui;

import java.awt.Graphics;
import se.plweb.memory.domain.GameObject;
import se.plweb.memory.domain.Position;
import se.plweb.memory.domain.Size;
import se.plweb.memory.gui.GameObjectGuiImpl.GUIState;

public interface GameObjectGui extends GameObject {
	void draw(Graphics graphics, Size gameObjectSize, GUIState guiState);
	boolean isPositionInsideOfGameObject(Position position);
}
