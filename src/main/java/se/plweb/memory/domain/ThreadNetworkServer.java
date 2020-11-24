package se.plweb.memory.domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.plweb.memory.gui.GamePlayerVsNetworkPlayer;

/**
 * 
 * @author Peter Lindblom
 */

public class ThreadNetworkServer extends AbstractThread implements Runnable {

	private static Logger logger;
	private Socket socket;
	private GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer;
	private int xSize;
	private int ySize;
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;
	private long timeStamp;

	public ThreadNetworkServer() {
		logger = Logger.getLogger(this.getClass().getName());
	}

	public void start(Socket socket,
			GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer, int xSize,
			int ySize) {
		this.socket = socket;
		this.gamePlayerVsNetworkPlayer = gamePlayerVsNetworkPlayer;
		this.xSize = xSize;
		this.ySize = ySize;

		if (isFirstTime()) {
			this.thread.start();
			setFirstTime(false);
			logger.log(Level.FINE, "firstTime");
		}

		if (!isRunning()) {
			setRunning(true);
			logger.log(Level.FINE, "this.isRunning = true;");
		}

	}

	public void run() {
		while (true) {
			if (isRunning()) {
				if (!socket.isConnected()) {
					gamePlayerVsNetworkPlayer.disConnected();
					logger.log(Level.FINE, "!socket.isConnected()");
				}

				try {
					isr = new InputStreamReader(socket.getInputStream());
					br = new BufferedReader(isr);
					pw = new PrintWriter(socket.getOutputStream());
					Scanner sc = new Scanner(br);

					pw.println(ProtocolConstants.SIZE_OF_BOARD
							+ ProtocolConstants.SPACE + xSize
							+ ProtocolConstants.SPACE + ySize);
					pw.flush();

					if (sc.next().equals(ProtocolConstants.START)) {
						gamePlayerVsNetworkPlayer.startGame();
						logger.log(Level.FINE, "gameMultiPlayer.startGame();");
					}

					while (isRunning()) {

						if (gamePlayerVsNetworkPlayer.getMatchedPairs() == gamePlayerVsNetworkPlayer
								.getTotalNumberOfParis()) {
							pw.println(ProtocolConstants.WON);
							pw.flush();
							gamePlayerVsNetworkPlayer.serverWon();
							logger.log(Level.FINE,
									"gamePlayerVsNetworkPlayer.serverWon();");
							break;
						} else {
							gamePlayerVsNetworkPlayer
									.updateStatusServer(
											gamePlayerVsNetworkPlayer
													.getMatchedPairs(),
											gamePlayerVsNetworkPlayer
													.getTotalNumberOfAttempts());
							pw
									.println(ProtocolConstants.MATCHEDPAIRS_AND_ATTEMPTS
											+ ProtocolConstants.SPACE
											+ gamePlayerVsNetworkPlayer
													.getMatchedPairs()
											+ ProtocolConstants.SPACE
											+ gamePlayerVsNetworkPlayer
													.getTotalNumberOfAttempts());
							pw.flush();

							if (sc.hasNext()) {
								String tmp = sc.next();
								if (tmp
										.equals(ProtocolConstants.MATCHEDPAIRS_AND_ATTEMPTS)) {
									gamePlayerVsNetworkPlayer
											.updateStatusClient(sc.nextInt(),
													sc.nextInt());
								} else if (tmp.equals(ProtocolConstants.WON)) {
									gamePlayerVsNetworkPlayer.clientWon();
									logger
											.log(Level.FINE,
													"gamePlayerVsNetworkPlayer.clientWon();");
									break;
								}
							}

							pw.println(ProtocolConstants.TIME_OUT
									+ ProtocolConstants.SPACE
									+ Calendar.getInstance().getTimeInMillis());
							pw.flush();

							if (sc.hasNext()) {
								if (sc.next()
										.equals(ProtocolConstants.TIME_OUT)) {
									Long tmpTimeStamp = sc.nextLong();
									if (tmpTimeStamp > timeStamp) {
										timeStamp = tmpTimeStamp;
									} else {
										gamePlayerVsNetworkPlayer
												.disConnected();
										logger.log(Level.FINE, "timeout");
										break;
									}
								}
							} else {
								gamePlayerVsNetworkPlayer.disConnected();
								logger.log(Level.FINE, "timeout2");
								break;
							}
						}
						Thread.sleep(30);
					}
				} catch (Exception e) {
					logger.log(Level.WARNING, e.getMessage());
					setRunning(false);
				}
			} else {
				try {
					Thread.sleep(30);
				} catch (Exception e) {
					logger.log(Level.WARNING, e.getMessage());
					setRunning(false);
				}
			}
		}
	}

}
