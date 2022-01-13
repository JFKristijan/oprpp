package hr.fer.zemris.java.gui.layouts;

/**Exception for Calulator implementation
 * @author Fran Kristijan Jelenčić
 *
 */
public class CalcLayoutException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**Exception constructor with string param
	 * @param str exception text
	 */
	public CalcLayoutException(String str) {
		super(str);
	}
	
	/**Exception constructor with Exception param
	 * @param e exception to be wrapped
	 */
	public CalcLayoutException(Exception e) {
		super(e);
	}
	
}
