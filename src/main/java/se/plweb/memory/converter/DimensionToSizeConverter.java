package se.plweb.memory.converter;

import se.plweb.memory.domain.Size;

import java.awt.*;


public class DimensionToSizeConverter {

    public static Size convert(Dimension dimension) {
        if (dimension != null) {
            return Size.create(dimension.width, dimension.height);
        }
        return Size.create(0, 0);
    }
}
