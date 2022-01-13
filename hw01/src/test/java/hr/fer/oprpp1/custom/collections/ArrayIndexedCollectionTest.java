package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.Test;



public class ArrayIndexedCollectionTest {
	
	@Test
	public void testConstructorException() {
		assertThrows(
				IllegalArgumentException.class,
				()->new ArrayIndexedCollection(0)
				);
	}
	
	@Test
	public void testConstructor() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		for(int i = 1;i<19;i++) coll.add(i);
		ArrayIndexedCollection coll2 = new ArrayIndexedCollection(coll);
		ArrayIndexedCollection coll3 = new ArrayIndexedCollection(coll,64);
		assertEquals(18, coll2.toArray().length);
		assertEquals(64, coll3.toArray().length);
	}
	
	@Test
	public void testAddingToArray() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(1);
		assertEquals(1, coll.toArray()[0]);
	}

	
	@Test
	public void testGetException() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, ()-> coll.get(5));
	}
	
	@Test
	public void testGetElementSuccess() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		coll.add(5);
		assertEquals(5, coll.get(0));
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		for(int i=0;i<15;i++) coll.add(i);
		coll.clear();
		assertArrayEquals(new Objects[16], coll.toArray());
		assertEquals(0, coll.size());
	}
	
	@Test
	public void testInsert() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		for(int i = 0 ; i < 5 ; i++) coll.add(i);
		coll.insert(10, 1);
		assertEquals(10, coll.get(1));
	}
	
	@Test
	public void testInsertException() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		for(int i = 0 ; i < 5 ; i++) coll.add(i);
		assertThrows(IndexOutOfBoundsException.class, ()-> coll.insert(10, 15));
		assertThrows(NullPointerException.class, ()-> coll.insert(null, 1));
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		for(int i = 0 ; i < 5 ; i++) coll.add(i);
		assertEquals(-1, coll.indexOf(1337));
		assertEquals(4, coll.indexOf(4));
	}
	
	@Test
	public void testRemove() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		for(int i = 0 ; i < 10 ; i++) coll.add(i);
		coll.remove(5);
		assertEquals(6, coll.toArray()[5]);
		assertEquals(4, coll.toArray()[4]);
		assertEquals(9,coll.toArray()[8]);
		assertThrows(IndexOutOfBoundsException.class, ()-> coll.remove(15));
	}
	
	
}
