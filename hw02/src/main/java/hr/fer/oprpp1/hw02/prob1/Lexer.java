package hr.fer.oprpp1.hw02.prob1;

/**Class that represents an implementation of a lexer
 * @author frank
 *
 */
public class Lexer {
	private char[] data; 
	private Token token; 
	private int currentIndex; 
	private LexerState state=LexerState.BASIC;
	
	/**Constructor that creates an instance of Lexer with the given text as data
	 * @param text
	 */
	public Lexer(String text) { 
		if(text==null)throw new NullPointerException();
		String txt = text.replace("\n"," ").replace("\t", " ").replace("\r", " ").replaceAll(" +", " ").trim();
		data=txt.toCharArray();
		currentIndex=0;
		
	}

	/**Method that gets the next token from given data
	 * @return Token token that has been analyzed from given data
	 * @throws LexerException if an error occurrs during lexical analysis
	 */
	public Token nextToken() { 
		if(token!=null&&token.getType().equals(TokenType.EOF))throw new LexerException("Pokusaj generiranja tokena nakon EOF-a");
		if(currentIndex>=data.length) {
			token = new Token(TokenType.EOF,null);
			return token;
		}
		
		StringBuilder sb = new StringBuilder();
		
		TokenType currentTokenType = ascertainTokenType(data[currentIndex]);
		if(data[currentIndex]=='\\') {
			currentTokenType = TokenType.WORD;
			doEscapeChar(sb);
		}
		
		while(currentIndex<data.length&&data[currentIndex]!=' ') {
			if(state.equals(LexerState.BASIC)) {
				if(currentTokenType==ascertainTokenType(data[currentIndex])) {
					sb.append(data[currentIndex++]);
				}else {
					if(currentIndex<data.length&&data[currentIndex]=='\\') {
						doEscapeChar(sb);
					}else {
						break;
					}
				}
			}else {
				if(data[currentIndex]=='#') {
					//state = LexerState.BASIC; 10x lakse ovako
					break;
				}

				sb.append(data[currentIndex++]);
			}
			

		}
		if(sb.length()==0&&data[currentIndex]=='#') {
			sb.append(data[currentIndex++]);
			currentTokenType=TokenType.SYMBOL;
		}
		if(currentIndex<data.length&&data[currentIndex]==' ')
			currentIndex++;
		
		String read = sb.toString();
		
		switch(currentTokenType) {
			case NUMBER:
				token = new Token(currentTokenType,tryLong(read));
				break;
			case WORD:
				token = new Token(currentTokenType,read);
				break;
			case SYMBOL:
				token = new Token(currentTokenType,read.charAt(0));
				if(read.length()>1)
					if(currentIndex==data.length) {
						currentIndex-=read.length()-1;
					}else {
						currentIndex-=read.length();
					}
				break;
			case EOF:
				throw new LexerException("Pokusaj generiranja token nakon EOF-a");
		}
		return token;

	}
		

		
	private void doEscapeChar(StringBuilder sb) {
		if(currentIndex+1>=data.length)throw new LexerException("Error tried to escape end of string");
		if(ascertainTokenType(data[++currentIndex])==TokenType.WORD)throw new LexerException("Invalid escape");
		sb.append(data[currentIndex++]);
	}
	
	private TokenType ascertainTokenType(char c) {
		if(state.equals(LexerState.EXTENDED))return TokenType.WORD;
		if(Character.isDigit(c))return TokenType.NUMBER;
		if(Character.isLetter(c))return TokenType.WORD;
		return TokenType.SYMBOL;
	}
	

	/**Method that returns the last generated token, can be called multiple times, does not cause generation of the next token
	 * @return Token current token
	 */
	public Token getToken() {return token;}
	
	/**Method that sets the state of the Lexer
	 * @param state state that the lexer is set to
	 * @throws NullPointerException if the given state is <code>null</code>
	 */
	public void setState(LexerState state) {
		if(state==null)throw new NullPointerException("State cannot be null");
		this.state=state;
	}
	
	private Long tryLong(String str) {
		try {
			return Long.parseLong(str);
		}catch(NumberFormatException e) {
			throw new LexerException("Ulaz ne valja");
		}
		
	}

}
