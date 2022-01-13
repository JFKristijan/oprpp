package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	@Test
	public void testConstructor() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		assertEquals(0, list.size());
		list.add("Hello world");
		//list.add(1919);
		ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>(3);
		//col.add(Integer.valueOf(20));
		col.add("New York");
		col.add("San Francisco");
		LinkedListIndexedCollection<String> list2 = new LinkedListIndexedCollection<>(list);
		assertArrayEquals(list.toArray(), list2.toArray());
		assertThrows(NullPointerException.class,()->new LinkedListIndexedCollection<String>((LinkedListIndexedCollection<String>)null));
		assertThrows(NullPointerException.class,()->new LinkedListIndexedCollection<String>((ArrayIndexedCollection<String>)null));
	}
	@Test
	public void testAdd() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		list.add("Hello");
		list.insert("HelloWorld",0);
		list.insert("Test", 1);
		list.remove(1);
		assertEquals("HelloWorld", list.toArray()[0]);
		assertThrows(NullPointerException.class, ()->list.add(null));
	}
	@Test
	public void testGet() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		list.add("Hello");
		assertEquals("Hello",list.get(0));
		assertThrows(IndexOutOfBoundsException.class, ()->list.get(15));
	}
	@Test
	public void testClear() {
		LinkedListIndexedCollection<Integer> list = new LinkedListIndexedCollection<>();
		for(int i=0;i<10;i++)list.add(i);
		list.clear();
		assertArrayEquals(new Object[0], list.toArray());
	}
	@Test
	public void testInsert() {
		LinkedListIndexedCollection<Integer> list = new LinkedListIndexedCollection<>();
		for(int i=0;i<5;i++)list.add(i);
		list.insert(15,3);
		assertArrayEquals(new Object[] {0,1,2,15,3,4}, list.toArray());
		assertThrows(IndexOutOfBoundsException.class, ()->list.insert(15, 7));
		assertThrows(NullPointerException.class,()->list.insert(null, 1));
	}
	@Test
	public void testIndexOf() {
		LinkedListIndexedCollection<Integer> list = new LinkedListIndexedCollection<>();
		for(int i=0;i<5;i++)list.add(i);
		assertEquals(3, list.indexOf(3));
		assertEquals(-1, list.indexOf(196));
		assertEquals(-1, list.indexOf(null));
	}
	@Test
	public void testRemove() {
		LinkedListIndexedCollection<Integer> list = new LinkedListIndexedCollection<>();
		for(int i=0;i<5;i++)list.add(i);
		list.remove(1);
		assertEquals(-1, list.indexOf(1));
		assertEquals(0, list.indexOf(0));
		assertEquals(1, list.indexOf(2));
		list.forEach(System.out::println);
		list.remove(3);
		list.remove(2);
		list.remove(1);
		list.remove(0);
	}
	
	
}
