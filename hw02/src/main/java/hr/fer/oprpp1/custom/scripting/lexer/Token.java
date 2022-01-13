package hr.fer.oprpp1.custom.scripting.lexer;

/**Class that is used as a token for Lexer implementation
 * @author frank
 *
 */
public class Token {
	private Object value;
	private TokenType type;
	public Token(TokenType type, Object value) {
		this.type=type;
		this.value=value;
	}
	/**Method that returns the value of this token
	 * @return Object that is the value of this object
	 */
	public Object getValue() {
		return value;
	}
	/**Method that returns the type of this token
	 * @return TokenType that this object is type of
	 */
	public TokenType getType() {
		return type;
	}
	/**Returns the string representation of this object
	 *
	 */
	public String toString() {
		return getType()+", "+getValue();
	}
}
