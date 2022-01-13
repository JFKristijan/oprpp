package hr.fer.oprpp1.hw02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;




public class LexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.getNextToken(), "Token was expected but null was returned.");
	}
	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}


	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(TokenType.EOF, lexer.getNextToken().getType(), "Empty input must generate only EOF token.");
	}


	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		Token token = lexer.getNextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}
	@Test
	public void testSomeText() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text");
		Token token = lexer.getNextToken();
		assertEquals(TokenType.TEXT, token.getType());
		assertEquals("This is sample text", token.getValue());
	}
	@Test
	public void testFor() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i 1 10 1 $}");
		Token token = lexer.getNextToken();
		assertEquals(TokenType.TAG, token.getType());
		assertEquals("{$", token.getValue());
		token = lexer.getNextToken();
		assertEquals(TokenType.FOR, token.getType());
		token = lexer.getNextToken();
		assertEquals(TokenType.VAR, token.getType());
		assertEquals("i", token.getValue());
		token = lexer.getNextToken();
		assertEquals(TokenType.INT, token.getType());
		assertEquals(1, token.getValue());
		token = lexer.getNextToken();
		assertEquals(TokenType.INT, token.getType());
		assertEquals(10, token.getValue());
		token = lexer.getNextToken();
		assertEquals(TokenType.INT, token.getType());
		assertEquals(1, token.getValue());
		token = lexer.getNextToken();
		assertEquals(TokenType.TAG, token.getType());
		assertEquals("$}", token.getValue());
		
	}
	@Test
	public void testEnd() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("{$ END      $}");
		Token token = lexer.getNextToken();
		assertEquals(TokenType.TAG, token.getType());
		assertEquals("{$", token.getValue());
		token = lexer.getNextToken();
		assertEquals(TokenType.VAR, token.getType());
		assertEquals("END", token.getValue());
		token = lexer.getNextToken();
		assertEquals(TokenType.TAG, token.getType());
		assertEquals("$}", token.getValue());
		
	}
	
}
