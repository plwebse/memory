package se.plweb.memory.gui;

import se.plweb.memory.domain.*;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;

/**
 * @author Peter Lindblom
 */

public class GameObjectGuiImpl implements GameObjectGui {

    private static final Color ACTIVE_BORDER_COLOR = Color.LIGHT_GRAY;
    private static final Color INACTIVE_BORDER_COLOR = Color.DARK_GRAY;
    private static final Color MATCHED_BORDER_COLOR = Color.RED;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color MATCHED_BACKGROUND_COLOR = Color.LIGHT_GRAY;
    private static final Color MOUSEOVER = Color.BLACK;
    private final GameObject gameObject;
    private final GuiHelper guiHelper;
    private int width, height, xTopLeft, yTopLeft;
    private static final Logger logger = Logger.getLogger(GameObjectGuiImpl.class.getName());

    public GameObjectGuiImpl(int value, Position position, GuiHelper guiHelper) {
        this.gameObject = GameObjectImpl.create(value, position);
        this.guiHelper = guiHelper;
    }

    @SuppressWarnings("SameParameterValue")
    protected GameObjectGuiImpl(int value, Position position, GuiHelper guiHelper, int width, int height, int xTopLeft, int yTopLeft) {
        this.gameObject = GameObjectImpl.create(value, position);
        this.guiHelper = guiHelper;
        this.width = width;
        this.height = height;
        this.xTopLeft = xTopLeft;
        this.yTopLeft = yTopLeft;
    }

    public int getValue() {
        return gameObject.getValue();
    }

    public void setValue(int value) {
        gameObject.setValue(value);
    }

    public Position getPosition() {
        return gameObject.getPosition();
    }

    public GameObjectState getState() {
        return gameObject.getState();
    }

    public void setState(GameObjectState value) {
        gameObject.setState(value);
    }


    private void paintBackground(Graphics graphics, Color backgroundColor, Color borderColor) {
        graphics.setColor(backgroundColor);
        graphics.fillRect(getXTopLeft(), getYTopLeft(), getWidth(), getHeight());
        graphics.setColor(borderColor);
        graphics.drawRect(getXTopLeft(), getYTopLeft(), getWidth() - 1, getHeight() - 1);
    }

    private void paintValue(Graphics graphics, int value) {
        graphics.setColor(guiHelper.getValueColor(value));
        paintString(graphics, String.valueOf(value));
    }

    private void paintString(Graphics graphics, String sValue) {
        Font currentFont = graphics.getFont();
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.setFont(getValueFontWithCorrectSize(currentFont));

        Point centerOfText = getCenterPointOfText(g2d, sValue);
        Point center = new Point(xTopLeft + centerOfText.x, yTopLeft + centerOfText.y);

        g2d.drawString(sValue, center.x, center.y);

        graphics.setFont(currentFont);
    }

    private Point getCenterPointOfText(Graphics2D g2d, String text) {
        FontRenderContext context = g2d.getFontRenderContext();
        TextLayout txt = new TextLayout(text, g2d.getFont(), context);
        Rectangle2D bounds = txt.getBounds();

        double x = (getWidth() - (bounds.getWidth())) / 2;
        x = x - bounds.getX();
        double y = (getHeight() - ((bounds.getHeight() - txt.getDescent()))) / 2;
        y += txt.getAscent() - txt.getDescent() * 2;

        return new Point((int) x, (int) y);
    }

    private Font getValueFontWithCorrectSize(Font baseFont) {
        float fontSize = (getHeight() / 2.2f);
        return baseFont.deriveFont(baseFont.getSize() + fontSize).deriveFont(Font.BOLD);
    }

    public boolean isInNormalState() {
        return this.gameObject.isInNormalState();
    }

    @Override
    public boolean isInDisabledState() {
        return this.gameObject.isInDisabledState();
    }

    public boolean hasTheSameValueAndNotTheSameCoordinates(GameObject gameObject) {
        return this.gameObject
                .hasTheSameValueAndNotTheSameCoordinates(gameObject);
    }

    public boolean hasTheSameValueAndTheSameCoordinates(GameObject gameObject) {
        return this.gameObject.hasTheSameValueAndTheSameCoordinates(gameObject);
    }

    public void draw(Graphics graphics, Size size, GUIState guiState) {
        if (size == null) {
            logger.warning("size is null");
            return;
        }

        width = size.getWidth();
        height = size.getHeight();

        xTopLeft = width * gameObject.getPosition().getXPos();
        yTopLeft = height * gameObject.getPosition().getYPos();

        switch (gameObject.getState()) {
            case NORMAL_STATE:
                paintBackground(graphics, BACKGROUND_COLOR, ACTIVE_BORDER_COLOR);
                break;
            case DISABLED_STATE:
                paintBackground(graphics, BACKGROUND_COLOR, INACTIVE_BORDER_COLOR);
                break;
            case MATCHED_STATE:
                paintBackground(graphics, MATCHED_BACKGROUND_COLOR, MATCHED_BORDER_COLOR);
                paintValue(graphics, gameObject.getValue());
                break;
            case PRESSED_STATE:
                paintBackground(graphics, BACKGROUND_COLOR, INACTIVE_BORDER_COLOR);
                paintValue(graphics, gameObject.getValue());
                break;
        }

        if (guiState.equals(GUIState.MOUSE_OVER)) {
            graphics.setColor(MOUSEOVER);
            graphics.drawRect((xTopLeft + 1), (yTopLeft + 1), (width - 3), (height - 3));
        }
    }

    public boolean isPositionInsideOfGameObject(Position position) {
        return position.getXPos() > getXTopLeft()
                && position.getXPos() <= (getXTopLeft() + getWidth())
                && position.getYPos() > getYTopLeft()
                && position.getYPos() <= (getYTopLeft() + getHeight());
    }


    public enum GUIState {NORMAL, MOUSE_OVER}

    @Override
    public String toString() {
        return gameObject.toString();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getXTopLeft() {
        return xTopLeft;
    }

    public int getYTopLeft() {
        return yTopLeft;
    }
}
