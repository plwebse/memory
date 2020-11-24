package se.plweb.memory.domain;

import java.awt.*;


public class DimensionToSizeConverter {

    public static Size convert(Dimension dimension) {
        if (dimension != null) {
            return new Size(dimension.width, dimension.height);
        }
        return new Size(0, 0);
    }
}
