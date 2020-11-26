package se.plweb.memory.domain;

/**
 * 
 * @author Peter Lindblom
 */

public enum ComputerPlayers {

	EASY("Easy"), HARD("Hard");

	private final String description;

	ComputerPlayers(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		return description;
	}

	public ComputerPlayer createComputerPlayer(int totalSize) {
		switch (this) {
		case HARD:
			return new ComputerPlayerImpl(totalSize);
		default:
			return new ComputerPlayerImpl(calculateEasy(totalSize));
		}
	}

	private int calculateEasy(int totalSize) {
		if (totalSize > 0) {
			return (totalSize / 10);
		}
		return 0;
	}
}
