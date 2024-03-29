package se.plweb.memory.domain;

import se.plweb.memory.gui.GamePlayerVsComputerPlayer;
import se.plweb.memory.gui.GamePlayerVsNetworkPlayer;
import se.plweb.memory.gui.GameSinglePlayer;

import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Peter Lindblom Lindblom
 */

public class ThreadControl {

    private static Logger logger;
    private static ThreadControl INSTANCE = null;
    private final ThreadWaitForNetworkClient threadWaitForNetworkClient = new ThreadWaitForNetworkClient();
    private final ThreadNetworkServer threadNetworkServer = new ThreadNetworkServer();
    private final ThreadNetworkClient threadNetworkClient = new ThreadNetworkClient();
    private final ThreadSinglePlayer threadSinglePlayer = new ThreadSinglePlayer();
    private final ThreadPlayerVsComputerPlayer threadPlayerVsComputerPlayer = new ThreadPlayerVsComputerPlayer();
    private final List<AbstractThread> threads;

    private ThreadControl() {
        logger = Logger.getLogger(this.getClass().getName());
        threads = Stream.of(threadWaitForNetworkClient, threadNetworkClient,
                threadNetworkServer, threadSinglePlayer,
                threadPlayerVsComputerPlayer)
                .collect(Collectors.toList());
    }

    public static synchronized ThreadControl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThreadControl();
        }
        logger.log(Level.FINE, "getInstance");
        return INSTANCE;
    }

    private synchronized List<AbstractThread> getAllThreads() {
        return threads;
    }

    public synchronized void startWaitForClient(
            GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer, int port,
            int xSize, int ySize) {
        logger.log(Level.FINE, "startWaitForClient");
        threadWaitForNetworkClient.start(gamePlayerVsNetworkPlayer, port, xSize, ySize);
    }

    public synchronized void startServer(Socket socket,
                                         GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer,
                                         int xSize,
                                         int ySize) {
        logger.log(Level.FINE, "startServer");
        threadNetworkServer.start(socket, gamePlayerVsNetworkPlayer, xSize,
                ySize);
    }

    public synchronized void startClient(
            GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer, String ip,
            int port) {
        logger.log(Level.FINE, "startClient");
        threadNetworkClient.start(gamePlayerVsNetworkPlayer, ip, port);
    }

    public synchronized void startSinglePlayer(GameSinglePlayer gameSinglePlayer) {
        logger.log(Level.FINE, "startSinglePlayer");
        threadSinglePlayer.start(gameSinglePlayer);
    }

    public synchronized void startPlayerVsComputer(
            GamePlayerVsComputerPlayer gamePlayerVsComputerPlayer) {
        logger.log(Level.FINE, "startSinglePlayer");
        threadPlayerVsComputerPlayer.start(gamePlayerVsComputerPlayer);
    }

    public synchronized void stopWaitForClient() {
        logger.log(Level.FINE, "stopWaitForClient");
        threadWaitForNetworkClient.stopThread();
    }

    public synchronized void stopAll() {
        logger.log(Level.FINE, "stopAll");
        getAllThreads().forEach(AbstractThread::stopThread);
    }

    public synchronized void stopApplication() {
        getAllThreads().forEach(AbstractThread::stopApplicationRunning);
    }
}