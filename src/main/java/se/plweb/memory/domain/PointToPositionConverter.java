package se.plweb.memory.domain;

import java.awt.Point;

public class PointToPositionConverter {

	public static Position convert(Point point){
		if(point != null){
			return Position.create(point.x, point.y);
		}
		return Position.create(0,0);
	}
	
}
