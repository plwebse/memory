package se.plweb.memory.converter;

import se.plweb.memory.domain.Position;

import java.awt.Point;
import java.util.Optional;

public class PointToPositionConverter {

    public static synchronized Position convert(Point point) {
        return Optional.ofNullable(point)
                .map(p -> Position.create(p.x, p.y))
                .orElseGet(() -> Position.create(-1, -1));
    }
}
