package hr.fer.oprpp1.custom.scripting.parser;

public class SmartScriptParserException extends RuntimeException {
	public SmartScriptParserException(Exception e) {
		super(e);
	}
	public SmartScriptParserException() {
		super();
	}
	public SmartScriptParserException(String str) {
		super(str);
	}
	
}
