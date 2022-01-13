package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;



public class ArrayIndexedCollectionTest {
	
	@Test
	public void testConstructorException() {
		assertThrows(
				IllegalArgumentException.class,
				()->new ArrayIndexedCollection<Integer>(0)
				);
	}
	
	@Test
	public void testConstructor() {
		ArrayIndexedCollection<Integer> coll = new ArrayIndexedCollection<Integer>();
		for(int i = 1;i<19;i++) coll.add(i);
		ArrayIndexedCollection<Integer> coll2 = new ArrayIndexedCollection<Integer>(coll);
		ArrayIndexedCollection<Integer> coll3 = new ArrayIndexedCollection<Integer>(coll,64);
		assertEquals(18, coll2.toArray().length);
		assertEquals(64, coll3.toArray().length);
	}
	
	@Test
	public void testAddingToArray() {
		ArrayIndexedCollection<Integer> coll = new ArrayIndexedCollection<>();
		coll.add(1);
		assertEquals(1, coll.toArray()[0]);
	}

	
	@Test
	public void testGetException() {
		ArrayIndexedCollection<Integer> coll = new ArrayIndexedCollection<>();
		assertThrows(IndexOutOfBoundsException.class, ()-> coll.get(5));
	}
	
	@Test
	public void testGetElementSuccess() {
		ArrayIndexedCollection<Integer> coll = new ArrayIndexedCollection<>();
		coll.add(5);
		assertEquals(5, coll.get(0));
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection<Integer> coll = new ArrayIndexedCollection<>();
		for(int i=0;i<15;i++) coll.add(i);
		ArrayIndexedCollection<Number> col1 = new ArrayIndexedCollection<Number>(coll);
		assertEquals(10, col1.get(10));
		//assertArrayEquals(new Integer[16], coll.toArray());
		assertEquals(15, coll.size());
	}
	
	@Test
	public void testInsert() {
		ArrayIndexedCollection<Integer> coll = new ArrayIndexedCollection<>();
		for(int i = 0 ; i < 5 ; i++) coll.add(i);
		coll.insert(10, 1);
		assertEquals(10, coll.get(1));
	}
	
	@Test
	public void testInsertException() {
		ArrayIndexedCollection<Integer> coll = new ArrayIndexedCollection<>();
		for(int i = 0 ; i < 5 ; i++) coll.add(i);
		assertThrows(IndexOutOfBoundsException.class, ()-> coll.insert(10, 15));
		assertThrows(NullPointerException.class, ()-> coll.insert(null, 1));
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection<Integer> coll = new ArrayIndexedCollection<>();
		for(int i = 0 ; i < 5 ; i++) coll.add(i);
		assertEquals(-1, coll.indexOf(1337));
		assertEquals(4, coll.indexOf(4));
	}
	
	@Test
	public void testRemove() {
		ArrayIndexedCollection<Integer> coll = new ArrayIndexedCollection<>();
		for(int i = 0 ; i < 10 ; i++) coll.add(i);
		coll.remove(5);
		assertEquals(6, coll.toArray()[5]);
		assertEquals(4, coll.toArray()[4]);
		assertEquals(9,coll.toArray()[8]);
		assertThrows(IndexOutOfBoundsException.class, ()-> coll.remove(15));
	}
	@Test
	public void testIterator() {
		ArrayIndexedCollection<Integer> coll = new ArrayIndexedCollection<>();
		for(int i = 0 ; i < 10 ; i++) coll.add(i);
		
		ElementsGetter<Integer> it = coll.createElementsGetter();
		coll.remove(5);
		assertThrows(ConcurrentModificationException.class,()-> it.hasNextElement());
		assertThrows(ConcurrentModificationException.class,()-> it.getNextElement());
		ElementsGetter<Integer> it2 = coll.createElementsGetter();
		it2.getNextElement();
		assertEquals(1, it2.getNextElement());
		
	}
}
