package se.plweb.memory.domain;

import static se.plweb.memory.domain.Position.create;
import junit.framework.TestCase;

public class GameObjectTest extends TestCase {

	public void testHasTheSameValueAndNotTheSameCoordinates1() {

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(8, 9));

		assertTrue(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testHasTheSameValueAndNotTheSameCoordinates2() {
		GameObject t1 = GameObjectImpl.create(1, create(8, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertTrue(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testHasTheSameValueAndNotTheSameCoordinates3() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 8));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertTrue(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testHasTheSameValueAndNotTheSameCoordinates4() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 8));

		assertTrue(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testHasTheSameValueAndNotTheSameCoordinatesError1() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(2, create(9, 9));

		assertFalse(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testHasTheSameValueAndNotTheSameCoordinatesError2() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertFalse(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	public void testHasTheSameValueAndTheSameCoordinates() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertTrue(t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	public void testHasTheSameValueAndTheSameCoordinatesError() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(8, 9));

		assertFalse(t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	public void testHasTheSameValueAndTheSameCoordinatesError2() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 8));

		assertFalse(t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	public void testHasTheSameValueAndTheSameCoordinatesError3() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(2, create(9, 9));

		assertFalse(t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	public void testIsInNormalState() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.NORMAL_STATE);
		assertTrue(t1.isInNormalState());
	}

	public void testIsInNormalStateError1() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.DISABLED_STATE);
		assertFalse(t1.isInNormalState());
	}

	public void testIsInNormalStateError2() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.PRESSED_STATE);
		assertFalse(t1.isInNormalState());
	}

	public void testIsInNormalStateError3() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.MATCHED_STATE);
		assertFalse(t1.isInNormalState());
	}

	public void testEquals() {

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertEquals(t1, t2);
	}

	public void testHashCode() {

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		assertEquals(t1.hashCode(), t2.hashCode());
	}

	public void testHashCodeError() {

		boolean actual;

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(8, 9));

		actual = (t1.hashCode() == t2.hashCode());

		assertFalse(actual);
	}
}
