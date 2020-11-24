package se.plweb.memory.domain;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.plweb.memory.gui.GamePlayerVsComputerPlayer;
import se.plweb.memory.gui.GamePlayerVsNetworkPlayer;
import se.plweb.memory.gui.GameSinglePlayer;

/**
 * 
 * @author Peter Lindblom Lindblom
 */

public class ThreadControl {

	private ThreadWaitForNetworkClient threadWaitForNetworkClient = new ThreadWaitForNetworkClient();
	private ThreadNetworkServer threadNetworkServer = new ThreadNetworkServer();
	private ThreadNetworkClient threadNetworkClient = new ThreadNetworkClient();
	private ThreadSinglePlayer threadSinglePlayer = new ThreadSinglePlayer();
	private ThreadPlayerVsComputerPlayer threadPlayerVsComputerPlayer = new ThreadPlayerVsComputerPlayer();

	private static Logger logger;

	private static ThreadControl INSTANCE = null;

	private ThreadControl() {
		logger = Logger.getLogger(this.getClass().getName());
	}

	public static synchronized ThreadControl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ThreadControl();
		}
		logger.log(Level.FINE, "getInstance");
		return INSTANCE;
	}

	@SuppressWarnings("unused")
	private Object readObject() {
		return INSTANCE;
	}

	public synchronized void startWaitForClient(
			GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer, int port,
			int xSize, int ySize) {
		logger.log(Level.FINE, "startWaitForClient");
		threadWaitForNetworkClient.start(gamePlayerVsNetworkPlayer, port,
				xSize, ySize);
	}

	public synchronized void startServer(Socket socket,
			GamePlayerVsNetworkPlayer gamePlayerVsNetworkPlayer, int xSize,
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

	public synchronized void stopSinglePlayer() {
		logger.log(Level.FINE, "stopSinglePlayer");
		threadSinglePlayer.stop();
	}

	public synchronized void stopWaitForClient() {
		logger.log(Level.FINE, "stopWaitForClient");
		threadWaitForNetworkClient.stop();
	}

	public synchronized void stopServer() {
		logger.log(Level.FINE, "stopServer");
		threadNetworkServer.stop();
	}

	public synchronized void stopClient() {
		logger.log(Level.FINE, "stopClient");
		threadNetworkClient.stop();
	}

	public synchronized void stopAll() {
		logger.log(Level.FINE, "stopAll");
		threadPlayerVsComputerPlayer.stop();
		threadNetworkServer.stop();
		threadNetworkClient.stop();
		threadWaitForNetworkClient.stop();
		threadSinglePlayer.stop();
	}
}