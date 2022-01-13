package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.hw02.prob1.LexerException;

/**Class that represents a Lexer for task 3 of our homework
 * @author frank
 *
 */
public class SmartScriptLexer {
	private char[] data;
	private int currentIndex;
	private Token token;
	private LexerState state = LexerState.TXT;
	public SmartScriptLexer(String str) {
		if(str==null)throw new NullPointerException("Input is null");
		data=str.toCharArray();
	}
	/**Returns the next token from the given data
	 * @return Token that is the next token that has been analyzed
	 */
	public Token getNextToken() {
		if(token!=null&&isOfType(TokenType.EOF))throw new LexerException("Tried to generate a token after EOFa");
		if(currentIndex>=data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		if(token==null&&data[currentIndex]=='{'&&data[currentIndex+1]=='$')state=LexerState.TAG;
		StringBuilder sb = new StringBuilder();
		boolean switcher = false;

		if(state==LexerState.TXT){
			while(currentIndex<data.length) {
				if(data[currentIndex]=='\\') {
					if(data[currentIndex+1]!='{'&&data[currentIndex+1]!='"'&&data[currentIndex+1]!='\\')throw new LexerException("Invalid escape");
					if(data[currentIndex]=='\\'&&data[currentIndex+1]=='{') {
						sb.append(data[currentIndex]+""+data[currentIndex+1]);
						currentIndex+=2;
					}

				}
				if(data[currentIndex]=='{'&&data[currentIndex+1]=='$') {
					switcher=true;
					break;
				}else {
					//counter=0;
					sb.append(data[currentIndex++]);
				}
			}
			token = new Token(TokenType.TEXT,sb.toString());
			if(switcher)state=LexerState.TAG;
			return token;


		}else {
			if(data[currentIndex]=='{'&&data[currentIndex+1]=='$') {
				sb.append(data[currentIndex]+""+data[currentIndex+1]);
				currentIndex+=2;
				token = new Token(TokenType.TAG, sb.toString());
				return token;
			}
			//dok smo u tag modu preskoci sve praznine
			while(data[currentIndex]==' '||data[currentIndex]=='\t'||data[currentIndex]=='\r'||data[currentIndex]=='\n') {
				currentIndex++;
			}
			if(data[currentIndex]=='=') {
				token = new Token(TokenType.ECHO,data[currentIndex++]);
				return token;
			}
			if((data[currentIndex]=='F'||data[currentIndex]=='f')&&
					(data[currentIndex+1]=='O'||data[currentIndex+1]=='o')&&
					(data[currentIndex+2]=='R'||data[currentIndex+2]=='r')&&data[currentIndex+3]==' ') {
				sb.append(data[currentIndex]+data[currentIndex+1]+data[currentIndex+2]);
				currentIndex+=3;
				token = new Token(TokenType.FOR,sb.toString());
				return token;
			}
			if(data[currentIndex]=='@') {
				currentIndex++;
				while(currentIndex<data.length&&data[currentIndex]!=' '&&data[currentIndex]!='\t'&&data[currentIndex]!='\r'&&data[currentIndex]!='\n'&&data[currentIndex]!='$') {
					sb.append(data[currentIndex++]);
				}
				token = new Token(TokenType.FUNC,sb.toString());
				return token;
			}
			if(data[currentIndex]=='"') {
				currentIndex++;
				try {
					while(currentIndex<data.length&&
							(data[currentIndex]!='"'||
							(data[currentIndex-1]=='\\'&&
							data[currentIndex]=='"'))) {
						if(data[currentIndex]=='\\'&&data[currentIndex+1]=='n') {
							sb.append("\n");
							currentIndex+=2;
							continue;
						}
						if(data[currentIndex]=='\\'&&data[currentIndex+1]=='r') {
							sb.append("\r");
							currentIndex+=2;
							continue;
						}
						//							if(data[currentIndex]=='\\'&&data[currentIndex+1]=='\\') {
						//								sb.append("\\");
						//								currentIndex+=2;
						//								continue;
						//							}
						if(data[currentIndex]=='\\'&&data[currentIndex+1]!='"'&&data[currentIndex+1]!=' '&&data[currentIndex+1]!='\\')throw new LexerException("Invalid escape");
						if(data[currentIndex]=='\\') {
							currentIndex++;
							sb.append(data[currentIndex++]);
						}else {
							sb.append(data[currentIndex++]);
						}

					}
				}catch(IndexOutOfBoundsException e) {
					throw new LexerException("String literals never closed");
				}
				currentIndex++;
				token = new Token(TokenType.STRING,sb.toString());
				return token;
			}
			if(Character.isLetter(data[currentIndex])) {
				while(currentIndex<data.length&&
						(Character.isLetter(data[currentIndex])||Character.isDigit(data[currentIndex])
								||data[currentIndex]=='_')&&
						data[currentIndex]!=' '&&
						data[currentIndex]!='\t'&&
						data[currentIndex]!='\r'&&
						data[currentIndex]!='\n'&&
						data[currentIndex]!='$') {
					sb.append(data[currentIndex++]);
				}
				token = new Token(TokenType.VAR,sb.toString());
				return token;
			}
			if((data[currentIndex]=='-'&&Character.isDigit(data[currentIndex+1]))||Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex++]);
				while(currentIndex<data.length&&(Character.isDigit(data[currentIndex])||data[currentIndex]=='.')&&data[currentIndex]!=' '&&data[currentIndex]!='\t'&&data[currentIndex]!='\r'&&data[currentIndex]!='\n'&&data[currentIndex]!='$') {
					sb.append(data[currentIndex++]);
				}
				String read = sb.toString();
				if(read.indexOf('.')==-1) {
					token = new Token(TokenType.INT,Integer.parseInt(read));
				}else {
					token = new Token(TokenType.DOUBLE,Double.parseDouble(read));
				}
				return token;
			}
			if("+-*/^".contains(Character.toString(data[currentIndex]))) {
				token = new Token(TokenType.OPERATOR,data[currentIndex++]);
				return token;
			}
			if(data[currentIndex]=='$'&&data[currentIndex+1]=='}') {
				state=LexerState.TXT;
				sb.append(data[currentIndex]+""+data[currentIndex+1]);
				currentIndex+=2;
				token = new Token(TokenType.TAG, sb.toString());
				return token;
			}
			throw new LexerException("Unable to create token from input");



		}
		//return token;
	}

	/**Method that returns the current token without advancing onto the next one
	 * @return
	 */
	public Token getToken() {return token;}


	private boolean isOfType(TokenType type) {
		return token.getType().equals(type);
	}
}
