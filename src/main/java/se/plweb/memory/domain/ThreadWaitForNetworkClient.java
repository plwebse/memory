package se.plweb.memory.domain;

import se.plweb.memory.gui.GamePlayerVsNetworkPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Peter Lindblom
 */

public class ThreadWaitForNetworkClient extends AbstractThread {

    private static Logger logger;
    private GamePlayerVsNetworkPlayer gameMultiPlayer;
    private int port;
    private int ySize;
    private int xSize;
    private ServerSocket serverSocket;

    public ThreadWaitForNetworkClient() {
        logger = Logger.getLogger(this.getClass().getName());
        this.setName(this.getClass().getName());
    }

    public void start(GamePlayerVsNetworkPlayer gameMultiPlayer, int port,
                      int xSize, int ySize) {

        this.gameMultiPlayer = gameMultiPlayer;
        this.port = port;
        this.ySize = ySize;
        this.xSize = xSize;

        if (isFirstTime()) {
            this.start();
            logger.log(Level.FINE, "start firstTime=true");
            setFirstTimeToFalse();
        }

        if (!isRunning()) {
            logger.log(Level.FINE, "start isRunning=false");
            setRunning(true);
        }
    }

    public void stopThread() {
        if (isRunning()) {
            logger.log(Level.FINE, "stop isRunning=true");
            closeServerSocket(serverSocket);
            setRunning(false);
        }
    }

    public void run() {
        while (isApplicationRunning() && isRunning()) {
            ThreadControl threadControl = ThreadControl.getInstance();
            logger.log(Level.FINE, "run() ");
            try {
                serverSocket = new ServerSocket(port);
                gameMultiPlayer.waitingForClient();
                logger.log(Level.FINE, "run waiting for client");
                threadControl.startServer(serverSocket.accept(),
                        gameMultiPlayer, xSize, ySize);
                closeServerSocket(serverSocket);
                threadControl.stopWaitForClient();
                logger.log(Level.FINE, "run complete");
            } catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage());
            } finally {
                closeServerSocket(serverSocket);
            }
        }
    }

    private void closeServerSocket(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                if (!serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
    }
}