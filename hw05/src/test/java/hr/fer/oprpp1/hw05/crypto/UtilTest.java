package hr.fer.oprpp1.hw05.crypto;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;




public class UtilTest {
	@Test
	public void testStringToHex() {
		Util.hextobyte("01aE22");
		assertArrayEquals(new byte[] {1, -82, 34},Util.hextobyte("01aE22"));
		assertArrayEquals(new byte[] {-6,-50},Util.hextobyte("FaCe"));
		assertArrayEquals(new byte[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},Util.hextobyte("000102030405060708090A0B0C0D0E0F"));
		assertArrayEquals(new byte[] {-85,-51,-17},Util.hextobyte("ABCDEF"));
		assertThrows(IllegalArgumentException.class, ()->Util.hextobyte("a"));
		
	}
	
	
	@Test
	public void testHexToString() {
		assertEquals("",Util.bytetohex(new byte[] {}));
		assertEquals("000102030405060708090a0b0c0d0e0f",Util.bytetohex(new byte[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}));
		assertEquals("ff00",Util.bytetohex(new byte[] {-1,0}));
		
	}
	
	
	
	
	
}
