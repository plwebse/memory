package se.plweb.memory.domain;

import static se.plweb.memory.domain.Position.create;
import org.junit.Assert;
import org.junit.Test;

public class GameObjectTest {

	@Test
	public void testHasTheSameValueAndNotTheSameCoordinates1() {

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(8, 9));

		Assert.assertTrue(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	@Test
	public void testHasTheSameValueAndNotTheSameCoordinates2() {
		GameObject t1 = GameObjectImpl.create(1, create(8, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		Assert.assertTrue(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	@Test
	public void testHasTheSameValueAndNotTheSameCoordinates3() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 8));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		Assert.assertTrue(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	@Test
	public void testHasTheSameValueAndNotTheSameCoordinates4() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 8));

		Assert.assertTrue(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	@Test
	public void testHasTheSameValueAndNotTheSameCoordinatesError1() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(2, create(9, 9));

		Assert.assertFalse(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	@Test
	public void testHasTheSameValueAndNotTheSameCoordinatesError2() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		Assert.assertFalse(t1.hasTheSameValueAndNotTheSameCoordinates(t2));
	}

	@Test
	public void testHasTheSameValueAndTheSameCoordinates() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		Assert.assertTrue(t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	@Test
	public void testHasTheSameValueAndTheSameCoordinatesError() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(8, 9));

		Assert.assertFalse(t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	@Test
	public void testHasTheSameValueAndTheSameCoordinatesError2() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 8));

		Assert.assertFalse(t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	@Test
	public void testHasTheSameValueAndTheSameCoordinatesError3() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(2, create(9, 9));

		Assert.assertFalse(t1.hasTheSameValueAndTheSameCoordinates(t2));
	}

	@Test
	public void testIsInNormalState() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.NORMAL_STATE);
		Assert.assertTrue(t1.isInNormalState());
	}

	@Test
	public void testIsInNormalStateError1() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.DISABLED_STATE);
		Assert.assertFalse(t1.isInNormalState());
	}

	@Test
	public void testIsInNormalStateError2() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.PRESSED_STATE);
		Assert.assertFalse(t1.isInNormalState());
	}

	@Test
	public void testIsInNormalStateError3() {
		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		t1.setState(GameObjectState.MATCHED_STATE);
		Assert.assertFalse(t1.isInNormalState());
	}

	@Test
	public void testEquals() {

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		Assert.assertEquals(t1, t2);
	}

	@Test
	public void testHashCode() {

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(9, 9));

		Assert.assertEquals(t1.hashCode(), t2.hashCode());
	}

	@Test
	public void testHashCodeError() {

		boolean actual;

		GameObject t1 = GameObjectImpl.create(1, create(9, 9));
		GameObject t2 = GameObjectImpl.create(1, create(8, 9));

		actual = (t1.hashCode() == t2.hashCode());

		Assert.assertFalse(actual);
	}
}
