package se.plweb.memory.domain;

import se.plweb.memory.gui.GamePlayerVsNetworkPlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Peter Lindblom
 */

public class ThreadNetworkClient extends AbstractThread {

    private static Logger logger;
    private GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer;
    private Socket socket;
    private long timeStamp;

    public ThreadNetworkClient() {
        logger = Logger.getLogger(this.getClass().getName());
        this.setName(ThreadWaitForNetworkClient.class.getName());
    }

    public void start(GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer,
                      String ipNumber, int portNumber) {
        this.gamePlayerVsNetworkPlayer = gamePlayerVsNetworkPlayer;

        if (isFirstTime()) {
            this.start();
            setFirstTimeToFalse();
        }

        if (!isRunning()) {
            try {
                socket = new Socket(ipNumber, portNumber);
                setRunning(true);
            } catch (Exception e) {
                this.gamePlayerVsNetworkPlayer.notConnected();
            }
        }
    }

    public void stopThread() {
        if (isRunning()) {
            setRunning(false);
        }
    }

    public void run() {
        while (isApplicationRunning() && isRunning()) {

            try {
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(br);

                if (sc.next().equals(ProtocolConstants.SIZE_OF_BOARD)) {
                    gamePlayerVsNetworkPlayer.makeGameBoard(sc.nextInt(), sc.nextInt());
                    pw.println(ProtocolConstants.START);
                    pw.flush();
                }

                gamePlayerVsNetworkPlayer.startGame();

                while (isRunning()) {

                    if (gamePlayerVsNetworkPlayer.getMatchedPairs() == gamePlayerVsNetworkPlayer
                            .getTotalNumberOfParis()) {
                        pw.println(ProtocolConstants.WON);
                        pw.flush();
                        gamePlayerVsNetworkPlayer.clientWon();
                        break;
                    } else {
                        gamePlayerVsNetworkPlayer.updateStatusClient(
                                gamePlayerVsNetworkPlayer.getMatchedPairs(),
                                gamePlayerVsNetworkPlayer.getTotalNumberOfAttempts()
                        );

                        if (sc.hasNext()) {
                            String tmp = sc.next();
                            if (tmp.equals(ProtocolConstants.MATCHED_PAIRS_AND_ATTEMPTS)) {
                                gamePlayerVsNetworkPlayer
                                        .updateStatusServer(sc.nextInt(),
                                                sc.nextInt());
                            } else if (tmp.equals(ProtocolConstants.WON)) {
                                gamePlayerVsNetworkPlayer.serverWon();
                                break;
                            }
                        }

                        pw.println(ProtocolConstants.MATCHED_PAIRS_AND_ATTEMPTS
                                + ProtocolConstants.SPACE
                                + gamePlayerVsNetworkPlayer
                                .getMatchedPairs()
                                + ProtocolConstants.SPACE
                                + gamePlayerVsNetworkPlayer
                                .getTotalNumberOfAttempts());
                        pw.flush();

                        if (sc.hasNext()) {
                            if (sc.next().equals(ProtocolConstants.TIME_OUT)) {
                                long tmpTimeStamp = sc.nextLong();
                                if (tmpTimeStamp > timeStamp) {
                                    timeStamp = tmpTimeStamp;
                                } else {
                                    gamePlayerVsNetworkPlayer.disConnected();
                                }
                            }
                        } else {
                            gamePlayerVsNetworkPlayer.disConnected();
                        }

                        pw.println(ProtocolConstants.TIME_OUT
                                + ProtocolConstants.SPACE
                                + Calendar.getInstance().getTimeInMillis());
                        pw.flush();
                    }
                }
            } catch (Exception e) {
                logger.log(Level.FINE, e.getMessage());
                break;
            }
        }
    }
}
