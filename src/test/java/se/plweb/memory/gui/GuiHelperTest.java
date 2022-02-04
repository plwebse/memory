package se.plweb.memory.gui;

import org.junit.Assert;
import org.junit.Test;
import se.plweb.memory.domain.GameBoard;
import se.plweb.memory.domain.GameBoardImpl;

public class GuiHelperTest {

    private final GameBoard gameBoard = new GameBoardImpl();
    private GuiHelper guiHelper;


    @Test
    public void sixTimesSix() {
        gameBoard.makeGameBoard(6, 6);
        guiHelper = GuiHelper.create(gameBoard.getTotalNumberOfPairs());

        Assert.assertEquals(1, guiHelper.getDisplayValue(1));
    }

    @Test
    public void tenTimesTen() {
        gameBoard.makeGameBoard(10, 10);
        guiHelper = GuiHelper.create(gameBoard.getTotalNumberOfPairs());

        Assert.assertEquals(13, guiHelper.getDisplayValue(49));
    }
}
