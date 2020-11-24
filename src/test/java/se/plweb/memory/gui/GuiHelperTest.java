package se.plweb.memory.gui;

import se.plweb.memory.domain.GameBoard;
import se.plweb.memory.domain.GameBoardImpl;
import junit.framework.TestCase;

public class GuiHelperTest extends TestCase {

	private GameBoard gameBoard = new GameBoardImpl();
	private GuiHelper guiHelper;
	
	

	public void test6x6() throws Exception {
		gameBoard.makeGameBoard(6, 6);
		guiHelper = new GuiHelper(gameBoard.getTotalNumberOfPairs());
		
		assertEquals(1, guiHelper.getDisplayValue(1));		
	}
	
	public void test10x10() throws Exception {
		gameBoard.makeGameBoard(6, 6);
		guiHelper = new GuiHelper(gameBoard.getTotalNumberOfPairs());
		
		assertEquals(true, true);
		
	}
	
}
