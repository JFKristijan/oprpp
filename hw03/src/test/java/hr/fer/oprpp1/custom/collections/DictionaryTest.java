package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void testConstructor() {
		assertEquals(0,new Dictionary<String, String>().size());
	}
	
	@Test
	public void testPutAndGet() {
		Dictionary<String, String> dict = new Dictionary<String, String>();
		dict.put("Hello", "world");
		assertEquals("world", dict.get("Hello"));
		assertEquals("world", dict.put("Hello", "value"));
		assertEquals("value", dict.get("Hello"));
		assertThrows(NullPointerException.class, ()->dict.put(null, "value"));
		assertNull(dict.get("world"));
	}
	
	@Test
	public void testRemoveAndClear() {
		Dictionary<String, String> dict = new Dictionary<String, String>();
		assertTrue(dict.isEmpty());
		dict.put("Hello", "world");
		dict.put("Another", "pair");
		assertEquals(2, dict.size());
		assertEquals("world", dict.remove("Hello"));
		assertNull(dict.get("Hello"));
		assertEquals(1,dict.size());
		assertEquals("pair", dict.get("Another"));
		dict.clear();
		assertTrue(dict.isEmpty());
	}
	@Test
	public void testSize() {
		Dictionary<String, String> dict = new Dictionary<String, String>();
		dict.put("Hello", "world");
		dict.put("Another", "pair");
		dict.put("Hello", ":)");
		dict.put("Signs","Feint");
		dict.put("Sticky", "keys");
		assertEquals(4, dict.size());
	}
	
	
	
	
}
