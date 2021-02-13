package se.plweb.memory.domain;

/**
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
        if (this == ComputerPlayers.HARD) {
            return new ComputerPlayerImpl(totalSize);
        }
        return new ComputerPlayerImpl(calculateEasy(totalSize));
    }

    private int calculateEasy(int totalSize) {
        return totalSize > 0 ? totalSize / 10 : 0;
    }
}
