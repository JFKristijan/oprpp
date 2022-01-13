package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Iterator;

import org.junit.jupiter.api.Test;


public class SimpleHashtableTest {
	@Test
	public void testConstructor() {
		SimpleHashtable<String, Double> sh = new SimpleHashtable<String, Double>();
		sh.put("hello", null);
		assertNull(sh.get("hello"));
	}
	
	@Test
	public void testPut() {
		SimpleHashtable<String, Integer> sh = new SimpleHashtable<String, Integer>();
		sh.put("Helo",5);
		sh.put("Wow", 4);
		sh.put("lol",1);
		sh.put("esfd", 34);
		String te = sh.toString();
		System.out.println(te);
		assertEquals("[Helo=5, lol=1, esfd=34, Wow=4]", te);
	}
	
}
