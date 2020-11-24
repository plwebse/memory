package se.plweb.memory.domain;

import se.plweb.memory.gui.GameSinglePlayer;

import java.util.logging.Logger;

/**
 * @author Peter Lindblom
 */

public class ThreadSinglePlayer extends AbstractThread implements Runnable {

    private static Logger logger;
    private GameSinglePlayer gameSinglePlayer;

    public ThreadSinglePlayer() {
        logger = Logger.getLogger(this.getClass().getName());
    }

    public void start(GameSinglePlayer gameSinglePlayer) {
        this.gameSinglePlayer = gameSinglePlayer;

        if (isFirstTime()) {
            thread.start();
            setFirstTime();
        }

        if (!isRunning()) {
            setRunning(true);
        }
    }

    public void run() {
        while (true) {
            if (isRunning()) {
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
                        Thread.sleep(30);
                    }
                } catch (Exception e) {

                    break;
                }
            } else {
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    logger.fine(e.getMessage());
                }
            }
        }
    }
}