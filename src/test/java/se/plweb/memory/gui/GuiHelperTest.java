package se.plweb.memory.gui;

import junit.framework.TestCase;
import se.plweb.memory.domain.GameBoard;
import se.plweb.memory.domain.GameBoardImpl;

public class GuiHelperTest extends TestCase {

    private GameBoard gameBoard = new GameBoardImpl();
    private GuiHelper guiHelper;


    public void test6x6() {
        gameBoard.makeGameBoard(6, 6);
        guiHelper = GuiHelper.create(gameBoard.getTotalNumberOfPairs());

        assertEquals(1, guiHelper.getDisplayValue(1));
    }

    public void test10x10() {
        gameBoard.makeGameBoard(10, 10);
        guiHelper = GuiHelper.create(gameBoard.getTotalNumberOfPairs());

        assertEquals(13, guiHelper.getDisplayValue(49));

    }

}
