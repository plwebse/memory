package se.plweb.memory.gui;

import se.plweb.memory.domain.*;

import java.awt.*;

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
    private static final int borderWidth = 5;
    private static final int borderWidthX2 = 10;
    private final GameObject gameObject;
    private final GuiHelper guiHelper;
    private int width, height, xTopLeft, yTopLeft;


    public GameObjectGuiImpl(int value, Position position, GuiHelper guiHelper) {
        gameObject = GameObjectImpl.create(value, position);
        this.guiHelper = guiHelper;
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

    @Override
    public String toString() {
        return gameObject.toString();
    }

    private void paintBackground(Graphics graphics, Color backgroundColor, Color borderColor) {
        graphics.setColor(backgroundColor);
        graphics.fillRect(xTopLeft, yTopLeft, width, height);
        graphics.setColor(borderColor);
        graphics.drawRect(xTopLeft, yTopLeft, width - 1, height - 1);
    }

    private void paintValue(Graphics graphics, int value) {
        graphics.setColor(guiHelper.getValueColor(value));
        paintValueShape(graphics, value);
    }

    private void paintValueShape(Graphics graphics, int value) {

        char[] cValue = String.valueOf(value).toCharArray();
        int cValueLength = cValue.length;

        int startX = calculateTopX();
        int startY = calculateTopY();

        int xOffset = startX + getStringStartOffset(width, cValueLength);
        int yOffset = startY + 20 + (height / 4);
        float fontSize = (height / 2.2f) / 10;

        paintString(graphics, cValue, cValueLength, xOffset, yOffset, fontSize);
    }

    private int getStringStartOffset(int width, int charLength) {
        return (width / 2) - (width / 4) * (charLength);
    }

    private void paintString(Graphics graphics, char[] chars, int charsLength, int x, int y, float fontSize) {
        Font currentFont = graphics.getFont();
        graphics.setFont(currentFont.deriveFont(currentFont.getSize() * fontSize).deriveFont(Font.BOLD));
        graphics.drawChars(chars, 0, charsLength, x, y);
        graphics.setFont(currentFont);
    }

    private int calculateTopX() {
        int topLeftX = xTopLeft + borderWidth;
        topLeftX += calculateCenterXPos();
        topLeftX = topLeftX - calculateHalfOfTotalValueWidth();
        return topLeftX;
    }

    private int calculateTopY() {
        int topLeftY = yTopLeft + borderWidth;
        topLeftY += calculateCenterYPos();
        topLeftY = topLeftY - calculateHalfOfTotalValueHeight();
        return topLeftY;
    }

    private int calculateHalfOfTotalValueWidth() {
        int valueWidth = (calculateValueObjectWidth() * 4);
        valueWidth = valueWidth + calculateValueObjectSpace(calculateValueObjectWidth() * 3);
        return divideBy2(valueWidth);
    }

    private int calculateHalfOfTotalValueHeight() {
        int valueHeight = (calculateValueObjectHeight() * 4);
        valueHeight = valueHeight + calculateValueObjectSpace(calculateValueObjectHeight() * 3);
        return divideBy2(valueHeight);
    }

    private int calculateCenterXPos() {
        return divideBy2(width - borderWidthX2);
    }

    private int calculateCenterYPos() {
        return divideBy2(height - borderWidthX2);
    }

    private int calculateValueObjectWidth() {
        return divideBy6(width - borderWidthX2);
    }

    private int calculateValueObjectHeight() {
        return divideBy6(height - borderWidthX2);
    }

    private int calculateValueObjectSpace(int valueObjectWidthOrHeight) {
        return divideBy2(valueObjectWidthOrHeight);
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
        if (size != null) {
            this.width = size.getWidth();
            this.height = size.getHeight();
        }

        xTopLeft = this.width * gameObject.getPosition().getXPos();
        yTopLeft = this.height * gameObject.getPosition().getYPos();

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

        return position != null
                && position.getXPos() >= xTopLeft
                && position.getXPos() < (xTopLeft + width)
                && position.getYPos() >= yTopLeft
                && position.getYPos() < (yTopLeft + height);
    }

    private int divideBy2(int number) {
        return (number / 2);
    }

    private int divideBy6(int number) {
        return (number / 6);
    }

    public enum GUIState {NORMAL, MOUSE_OVER}

}
