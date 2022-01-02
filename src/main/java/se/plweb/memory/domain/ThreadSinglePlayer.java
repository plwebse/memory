package se.plweb.memory.domain;

import se.plweb.memory.gui.GameSinglePlayer;

/**
 * @author Peter Lindblom
 */

public class ThreadSinglePlayer extends AbstractThread {

    private GameSinglePlayer gameSinglePlayer;

    public ThreadSinglePlayer() {
        this.setName(this.getClass().getName());
    }

    public void start(GameSinglePlayer gameSinglePlayer) {
        this.gameSinglePlayer = gameSinglePlayer;

        if (isFirstTime()) {
            start();
            setFirstTimeToFalse();
        }

        if (!isRunning()) {
            setRunning(true);
        }
    }

    public void run() {
        while (isApplicationRunning() && isRunning()) {
            try {
                gameSinglePlayer.startGame();
                while (true) {
                    if (gameSinglePlayer.getMatchedPairs() == gameSinglePlayer
                            .getTotalNumberOfParis()) {
                        gameSinglePlayer.singlePlayerWon();
                        break;
                    } else {
                        gameSinglePlayer.updateStatusSinglePlayerStatus(
                                gameSinglePlayer.getMatchedPairs(),
                                gameSinglePlayer.getNumberOfAttempts());
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
    }
}