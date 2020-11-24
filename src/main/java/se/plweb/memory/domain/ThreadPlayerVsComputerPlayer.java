package se.plweb.memory.domain;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.plweb.memory.gui.GamePlayerVsComputerPlayer;

/**
 * 
 * @author Peter Lindblom
 */

public class ThreadPlayerVsComputerPlayer extends AbstractThread implements
		Runnable {

	private static Logger logger;
	private GamePlayerVsComputerPlayer gamePlayerVsComputerPlayer;

	public ThreadPlayerVsComputerPlayer() {
		logger = Logger.getLogger(this.getClass().getName());
	}

	public void start(GamePlayerVsComputerPlayer gamePlayerVsComputerPlayer) {
		this.gamePlayerVsComputerPlayer = gamePlayerVsComputerPlayer;

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
						Thread.sleep(30);
					}
				} catch (Exception e) {
					break;
				}
			} else {
				try {
					Thread.sleep(30);
				} catch (Exception e) {
					logger.log(Level.FINE, e.getMessage());
				}
			}
		}
	}

}