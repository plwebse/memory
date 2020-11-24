package se.plweb.memory.domain;

/**
 * @author Peter Lindblom
 * 
 */

public class Position implements Cloneable {
	private int xPos;
	private int yPos;

	public Position() {
		xPos = 0;
		yPos = 0;
	}

	public Position(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	private Position(Position position) {
		this.xPos = position.getXPos();
		this.yPos = position.getYPos();
	}

	public synchronized int getXPos() {
		return xPos;
	}

	public synchronized void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public synchronized int getYPos() {
		return yPos;
	}

	public synchronized void setYPos(int yPos) {
		this.yPos = yPos;
	}

	public synchronized void moveUp() {
		moveUp(1);
	}

	public synchronized void moveUp(int numberOfSteps) {
		setYPos(getYPos() - numberOfSteps);
	}

	public synchronized void moveRight() {
		moveRight(1);
	}

	public synchronized void moveRight(int numberOfSteps) {
		setXPos(getXPos() + numberOfSteps);
	}

	public synchronized void moveDown() {
		moveDown(1);
	}

	public synchronized void moveDown(int numberOfSteps) {
		setYPos(getYPos() + numberOfSteps);
	}

	public synchronized void moveLeft() {
		moveLeft(1);
	}

	public synchronized void moveLeft(int numberOfSteps) {
		setXPos(getXPos() - numberOfSteps);
	}

	public Position clone() {
		Position position = (Position) super.clone();
		return new Position(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xPos;
		result = prime * result + yPos;
		return result;
	}

	@Override
	public synchronized boolean equals(Object obj) {
        return obj instanceof Position
                && this.getXPos() == ((Position) obj).getXPos()
                && this.getYPos() == ((Position) obj).getYPos();
    }

	public static Position create(int xPos, int yPos) {
		return new Position(xPos, yPos);
	}

	public String toString() {
		return "Position x:" + getXPos() + " y:" + getYPos() + "\n";
	}
}
