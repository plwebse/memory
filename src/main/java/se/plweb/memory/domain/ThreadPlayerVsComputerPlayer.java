package se.plweb.memory.domain;


import se.plweb.memory.gui.GamePlayerVsComputerPlayer;

/**
 * @author Peter Lindblom
 */

public class ThreadPlayerVsComputerPlayer extends AbstractThread {

    private GamePlayerVsComputerPlayer gamePlayerVsComputerPlayer;

    public ThreadPlayerVsComputerPlayer() {
        this.setName(this.getClass().getName());
    }

    public void start(GamePlayerVsComputerPlayer gamePlayerVsComputerPlayer) {
        this.gamePlayerVsComputerPlayer = gamePlayerVsComputerPlayer;

        if (isFirstTime()) {
            this.start();
            setFirstTimeToFalse();
        }

        if (!isRunning()) {
            setRunning(true);
        }
    }

    public void run() {
        while (isApplicationRunning() && isRunning()) {

            try {
                while (true) {
                    if (gamePlayerVsComputerPlayer
                            .getHumanPlayersMatchedPairs() == gamePlayerVsComputerPlayer
                            .getTotalNumberOfParis()) {
                        gamePlayerVsComputerPlayer.humanPlayerWon();
                        break;
                    } else if (gamePlayerVsComputerPlayer
                            .getComputerPlayersMatchedPairs() == gamePlayerVsComputerPlayer
                            .getTotalNumberOfParis()) {
                        gamePlayerVsComputerPlayer.computerPlayerWon();
                    } else {
                        gamePlayerVsComputerPlayer.updateHumanPlayerStatus(
                                gamePlayerVsComputerPlayer
                                        .getHumanPlayersMatchedPairs(),
                                gamePlayerVsComputerPlayer
                                        .getHumanPlayersNumberOfAttempts());
                        gamePlayerVsComputerPlayer
                                .updateComputerPlayerStatus(
                                        gamePlayerVsComputerPlayer
                                                .getComputerPlayersMatchedPairs(),
                                        gamePlayerVsComputerPlayer
                                                .getComputerPlayersNumberOfAttempts());
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
    }

}