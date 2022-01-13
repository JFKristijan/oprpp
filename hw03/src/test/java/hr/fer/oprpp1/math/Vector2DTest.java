package hr.fer.oprpp1.math;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;



public class Vector2DTest {

	@Test
	public void testConstructor() {
		Vector2D vector = new Vector2D(0, 0);
		//tests whether the given values are initialized properly
		assertEquals(0, vector.getX()+vector.getY());
	}
	
	@Test
	public void testGetters() {
		Vector2D vector = new Vector2D(1, 2);
		assertEquals(1, vector.getX());
		assertEquals(2, vector.getY());
	}
	
	@Test
	public void testAddAndAdded() {
		Vector2D vectorOne = new Vector2D(1,2);
		Vector2D vectorTwo = new Vector2D(3,4);
		vectorOne.add(vectorTwo);
		assertEquals(new Vector2D(4,6),vectorOne);
		Vector2D vectorThree = vectorTwo.added(vectorTwo);
		assertEquals(new Vector2D(3,4), vectorTwo);
		assertEquals(new Vector2D(6,8), vectorThree);
		
	}
	@Test
	public void testRotateAndRotated() {
		Vector2D vectorOne = new Vector2D(0,1);
		Vector2D vectorTwo = new Vector2D(1,0);
		vectorOne.rotate(Math.PI);
		assertEquals(new Vector2D(0,-1), vectorOne);
		Vector2D vectorThree = vectorTwo.rotated(3*Math.PI);
		assertEquals(new Vector2D(-1,0), vectorThree);
		assertEquals(new Vector2D(1,0), vectorTwo);
	}
	
	@Test
	public void testScaleAndScaled() {
		Vector2D vectorOne = new Vector2D(1,1);
		Vector2D vectorTwo = new Vector2D(2,2);
		vectorOne.scale(2);
		assertEquals(vectorTwo, vectorOne);
		Vector2D vectorThree = vectorTwo.scaled(3);
		assertEquals(new Vector2D(6,6), vectorThree);
		assertEquals(new Vector2D(2,2), vectorTwo);
	}
	@Test
	public void testCopy() {
		Vector2D vectorOne = new Vector2D(1,1);
		Vector2D vectorTwo = vectorOne.copy();
		vectorOne.scale(15);
		assertEquals(new Vector2D(1,1), vectorTwo);
		assertEquals(new Vector2D(15,15), vectorOne);
	}
	
	
}
