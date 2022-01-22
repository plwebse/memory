package se.plweb.memory.gui;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class GuiHelper {

    private final ColorValue[] colorValues;
    private final int[] displayValues = new int[ValueColor.values().length];

    private GuiHelper(int totalNumberOfPairs) {
        colorValues = new ColorValue[totalNumberOfPairs];

        for (int value = 1; value <= totalNumberOfPairs; value++) {
            int colorIndex = getColorIndex(value);
            displayValues[colorIndex]++;

            colorValues[getArrayIndex(value)] = new ColorValue(
                    displayValues[colorIndex],
                    ValueColor.getValueColor(colorIndex)
            );
        }
    }

    public static GuiHelper create(int totalNumberOfPairs) {
        return new GuiHelper(totalNumberOfPairs);
    }

    private ColorValue getColorValueFor(int value) {
        return colorValues[getArrayIndex(value)];
    }

    private int getArrayIndex(int value) {
        return (value - 1);
    }

    private int getColorIndex(int value) {
        return (value % 4);
    }

    public Color getValueColor(int value) {
        return getColorValueFor(value).getValueColor().getColor();
    }

    public int getDisplayValue(int value) {
        ColorValue colorValue = getColorValueFor(value);
        return Optional.ofNullable(colorValue)
                .map(ColorValue::getDisplayValue).orElse(-1);
    }

    @Override
    public String toString() {
        return "GuiHelper [colorValues=" + Arrays.toString(colorValues) + ", displayValues="
                + Arrays.toString(displayValues) + "]";
    }

    private enum ValueColor {
        BLACK(Color.BLACK, 0),
        RED(new Color(175, 0, 0), 1),
        GREEN(new Color(0, 175, 0), 2),
        BLUE(new Color(0, 0, 175), 3);

        private final Color color;
        private final int index;

        ValueColor(Color color, int index) {
            this.color = color;
            this.index = index;
        }

        public Color getColor() {
            return color;
        }

        public int getIndex() {
            return index;
        }

        public static ValueColor getValueColor(int byIndex) {
            return Arrays.stream(ValueColor.values())
                    .filter(valueColor -> valueColor.getIndex() == byIndex).findFirst()
                    .orElse(null);
        }
    }

    static class ColorValue {

        private final int displayValue;
        private final ValueColor valueColor;

        public ColorValue(int displayValue, ValueColor valueColor) {
            this.displayValue = displayValue;
            this.valueColor = valueColor;
        }

        public int getDisplayValue() {
            return displayValue;
        }

        public ValueColor getValueColor() {
            return valueColor;
        }

        @Override
        public String toString() {
            return "displayValue:" + displayValue;
        }
    }

}
