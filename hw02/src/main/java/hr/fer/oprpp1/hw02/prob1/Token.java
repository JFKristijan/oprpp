package hr.fer.oprpp1.hw02.prob1;

/**Tokens that are created by the {@link Lexer} class, used for parsing
 * @author frank
 *
 */
public class Token {
	private TokenType type;
	private Object value;
		
	public Token(TokenType type, Object value) {
		this.type=type;
		this.value=value;
	}
	/**Returns the value of the this token
	 * @return Object that is the value of the token
	 */
	public Object getValue() {return value;}
	/**Returns the type of the this token
	 * @return TokenType that is the value of the token
	 */
	public TokenType getType() {return type;}
	
	/**Method that returns the token in string form
	 *
	 */
	public String toString() {
		return "("+getType()+", "+getValue()+")";
	}

}
