package se.plweb.memory.gui;

/**
 * 
 * @author Peter Lindblom
 */

public enum GamePanel {

	singlePlayerPanel("singlePlayerPanel")
	, playerVsNetworkPlayerPanel("playerVsNetworkPlayerPanel")
	, playerVsComputerPlayerPanel("playerVsComputerPlayerPanel")
	, aboutTheGamePanel("aboutTheGamePanel");
	
	private String toString;
	
	private GamePanel(String toString) {
		this.toString = toString;		
	}
	
	@Override
	public String toString() {
		return toString;
	}
}
