package hr.fer.oprpp1.hw05.crypto;

/**Utility class used for converting hex strings to byte arrays and vice versa
 * @author Fran Kristijan Jelenčić
 *
 */
public class Util {
	
	private final static String VALUES = "0123456789abcdef";
	
	/**Method turns string input in hex form into a byte array that the string represents
	 * @param keyText hex string to be turned into a byte array
	 * @return byte[]
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length()%2==1)throw new IllegalArgumentException("Length of key text must be even number!");
		
		keyText=keyText.toLowerCase();
		byte[] retArr = new byte[keyText.length()/2];
		
		for(int i = 0 ; i < keyText.length(); i+=2) {
			retArr[i/2]=(byte) ((VALUES.indexOf(keyText.charAt(i))<<4)
					+(VALUES.indexOf(keyText.charAt(i+1))));
		}
		return retArr;
	}
	
	/**Method turns byte array into its string representation
	 * @param bytearray
	 * @return String the hex representation of given byte array
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i<bytearray.length ; i++) {
			int num = bytearray[i]&0xFF;
			sb.append(VALUES.charAt(num>>>4));
			sb.append(VALUES.charAt(num&0x0F));
			//sb.append(VALUES.charAt(num<<4>>>4&0x0F));
		}
		return sb.toString();
	}
}