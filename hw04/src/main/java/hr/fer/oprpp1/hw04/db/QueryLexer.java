package hr.fer.oprpp1.hw04.db;



/**Class that represents a lexer, tokens are strings
 * @author Fran Kristijan Jelenčić
 *
 */
public class QueryLexer {
	private String[] data;
	private int index;
	public QueryLexer(String data) {
		this.data = data.replaceAll("[!=><]{1,2}|AND|(?i)LIKE", " $0 ").replaceAll("\\s+", " ").trim().split(" ");// mega super duper metoda da osigura space oko keywordova
	}
	
	/**Returns next token
	 * @return String token
	 */
	public String getNextToken() {
		return data[index++];
	}
	
	/**Checks if more tokens exist
	 * @return true if more tokens exist, false otherwise
	 */
	public boolean hasNextToken() {
		return index<data.length;
	}
}
