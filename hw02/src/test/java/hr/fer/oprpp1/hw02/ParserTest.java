package hr.fer.oprpp1.hw02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class ParserTest {
	@Test
	public void testNotNull() {
		SmartScriptParser parser = new SmartScriptParser("");
		assertNotNull(parser.getDocumentNode(), "Token was expected but null was returned.");
	}
	@Test
	public void testNull() {
		
		assertThrows(NullPointerException.class, ()->new SmartScriptParser(null));
	}
	@Test
	public void testSomeText() {
		SmartScriptParser parser = new SmartScriptParser("Sample text");
		assertEquals("Sample text", parser.getDocumentNode().toString());
	}
	@Test
	public void testSomeTextForError() {
		//SmartScriptParser parser = new SmartScriptParser("Sample text\n{$ FOR     1 10 1 $}");
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser("Sample text\n{$ FOR     1 10 1 $}"));
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser("Sample text\n{$ FOR   i  1  342 10 1 $}"));
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser("Sample text\n{$ FOR$}"));
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser("This is sample text.\r\n"
				+ "{$ FOR i 1 10 1$}").getDocumentNode());
	}
	@Test
	public void testSomeWrongTag() {
		assertThrows(SmartScriptParserException.class, ()->new SmartScriptParser("{$END$}"));
	}
	@Test
	public void testExample() {
		SmartScriptParser parser = new SmartScriptParser("This is sample text.\r\n"
				+ "{$ FOR i 1 10 1$}"+ "{$END$}");
		assertEquals(2, parser.getDocumentNode().numberOfChildren());
	}
	
}
