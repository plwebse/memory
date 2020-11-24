package se.plweb.memory.domain;

import static se.plweb.memory.domain.Position.create;
import junit.framework.TestCase;

public class GameObjectTest extends TestCase {

	public void testhasTheSameValueAndNotTheSameCoordinates1() {

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(8, 9));

		assertEquals(true, t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testhasTheSameValueAndNotTheSameCoordinates2() {
		GameObject t1 = GameObjectImpl.create(1, create(8, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertEquals(true, t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testhasTheSameValueAndNotTheSameCoordinates3() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 8));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertEquals(true, t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testhasTheSameValueAndNotTheSameCoordinates4() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 8));

		assertEquals(true, t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testhasTheSameValueAndNotTheSameCoordinatesError1() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(2, create(9, 9));

		assertEquals(false, t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testhasTheSameValueAndNotTheSameCoordinatesError2() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertEquals(false, t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testhasTheSameValueAndTheSameCoordinates() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertEquals(true, t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	public void testhasTheSameValueAndTheSameCoordinatesError() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(8, 9));

		assertEquals(false, t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	public void testhasTheSameValueAndTheSameCoordinatesError2() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 8));

		assertEquals(false, t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	public void testhasTheSameValueAndTheSameCoordinatesError3() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(2, create(9, 9));

		assertEquals(false, t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	public void testIsInNormalState() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.NORMAL_STATE);
		assertEquals(true, t1.isInNormalState());
	}

	public void testIsInNormalStateError1() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.DISABLED_STATE);
		assertEquals(false, t1.isInNormalState());
	}

	public void testIsInNormalStateError2() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.PRESSED_STATE);
		assertEquals(false, t1.isInNormalState());
	}

	public void testIsInNormalStateError3() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.MATCHED_STATE);
		assertEquals(false, t1.isInNormalState());
	}

	public void testEquals() {

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertEquals(true, t1.equals(t2));
	}

	public void testHashCode() {

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertEquals(t1.hashCode(), t2.hashCode());
	}

	public void testHashCodeError() {

		boolean expected = false;
		boolean actual = false;

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(8, 9));

		actual = (t1.hashCode() == t2.hashCode());

		assertEquals(expected, actual);
	}
}
