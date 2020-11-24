package se.plweb.memory.domain;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.*;
import se.plweb.memory.gui.GamePlayerVsNetworkPlayer;

/**
 * 
 * @author Peter Lindblom
 */

public class ThreadWaitForNetworkClient extends AbstractThread implements
		Runnable {

	private GamePlayerVsNetworkPlayer gameMultiPlayer;
	private int port;
	private int ySize;
	private int xSize;
	private ServerSocket serverSocket;
	private static Logger logger;

	public ThreadWaitForNetworkClient() {
		logger = Logger.getLogger(this.getClass().getName());
	}

	public void start(GamePlayerVsNetworkPlayer gameMultiPlayer, int port,
			int ySize, int xSize) {

		this.gameMultiPlayer = gameMultiPlayer;
		this.port = port;
		this.ySize = ySize;
		this.xSize = xSize;

		if (isFirstTime()) {
			this.thread.start();
			logger.log(Level.FINE, "start firstTime=true");
			setFirstTime(false);
		}

		if (!isRunning()) {
			logger.log(Level.FINE, "start isRunning=false");
			setRunning(true);
		}
	}

	public void stop() {
		if (isRunning()) {
			logger.log(Level.FINE, "stop isRunning=true");
			closeServerSocket(serverSocket);
			setRunning(false);
		}
	}

	public void run() {
		while (true) {
			if (isRunning()) {

				ThreadControl threadControl = ThreadControl.getInstance();
				logger.log(Level.FINE, "run() ");
				try {
					serverSocket = new ServerSocket(port);
					gameMultiPlayer.waitingForClient();
					logger.log(Level.FINE, "run väntar på klient");
					threadControl.startServer(serverSocket.accept(),
							gameMultiPlayer, ySize, xSize);
					closeServerSocket(serverSocket);
					threadControl.stopWaitForClient();
					logger.log(Level.FINE, "run färdig");
				} catch (IOException e) {
					logger.log(Level.WARNING, e.getMessage());
				} catch (Exception e) {
					logger.log(Level.WARNING, e.getMessage());
				} finally {
					closeServerSocket(serverSocket);
				}
			} else {
				try {
					Thread.sleep(30);
				} catch (Exception e) {
					logger.log(Level.WARNING, e.getMessage());
				}
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
			} finally {
				serverSocket = null;
			}
		}
	}
}