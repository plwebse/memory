package se.plweb.memory.gui;

/**
 * 
 * @author Peter Lindblom
 */

public enum GameBoardDimension {

	SIX_TIMES_SIX("6x6", 6, 6),
	TEN_TIMES_TEN("10x10", 10, 10);

	private final String description;
	private final int xSize;
	private final int ySize;

	GameBoardDimension(String description, int xSize, int ySize) {
		this.description = description;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public String getDescription() {
		return description;
	}

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}

	@Override
	public String toString() {
		return getDescription();
	}
}
