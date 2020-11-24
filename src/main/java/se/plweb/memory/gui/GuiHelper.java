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
            int color = getColorIndex(value);
            displayValues[color]++;
            colorValues[getArrayIndex(value)] = new ColorValue(color, displayValues[color]);
        }
    }

    public static GuiHelper create(int totalNumberOfPairs){
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
        ColorValue colorValue = getColorValueFor(value);
        if (colorValue != null) {
            switch (colorValue.getColor()) {
                case 0:
                    return ValueColor.BLACK.getColor();
                case 1:
                    return ValueColor.RED.getColor();
                case 2:
                    return ValueColor.GREEN.getColor();
                case 3:
                    return ValueColor.BLUE.getColor();
            }
        }

        return null;
    }

    public int getDisplayValue(int value) {
        ColorValue colorValue = getColorValueFor(value);
        return Optional.ofNullable(colorValue)
                .map(ColorValue::getDisplayValue).orElse(-1);
    }

    @Override
    public String toString() {
        return "GuiHelper [colorValues=" + colorValues + ", displayValues="
                + Arrays.toString(displayValues) + "]";
    }

    private enum ValueColor {
        BLACK(Color.BLACK),
        RED(Color.RED),
        GREEN(Color.GREEN),
        BLUE(Color.BLUE);

        private final Color color;

        ValueColor(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    static class ColorValue {

        private final int color;
        private final int displayValue;

        public ColorValue(int color, int displayValue) {
            this.color = color;
            this.displayValue = displayValue;
        }

        public int getColor() {
            return color;
        }

        public int getDisplayValue() {
            return displayValue;
        }

        @Override
        public String toString() {
            return "color:" + color + ", displayValue:" + displayValue;
        }
    }

}
