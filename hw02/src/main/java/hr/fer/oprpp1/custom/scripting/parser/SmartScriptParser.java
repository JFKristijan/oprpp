package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

import java.util.ArrayList;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**Class this is a parser for {@link SmartScriptLexer}'s tokens 
 * @author frank
 *
 */
public class SmartScriptParser {
	private SmartScriptLexer lex;
	private ObjectStack document;
	private DocumentNode dn;
	
	/**Constructor that accepts 
	 * @param body
	 */
	public SmartScriptParser(String body) {
		lex = new SmartScriptLexer(body);
		document = new ObjectStack();
		try {
			parse();	
		}catch(Exception e) {
			throw new SmartScriptParserException(e);
		}
		
	}
	public void parse() {
		Node currNode = new DocumentNode();
		dn=(DocumentNode) currNode;
		document.push(currNode);
		Token curr= lex.getNextToken();
		while(curr.getType()!=TokenType.EOF) {
			switch(curr.getType()) {
			case TEXT:
				TextNode text = new TextNode((String)curr.getValue());
				currNode.addChildNode(text);
				curr=lex.getNextToken();
				break;
			case TAG:
				curr=lex.getNextToken();
				if(curr.getType()==TokenType.FOR) {
					ForLoopNode fln = parseFor();
					currNode.addChildNode(fln);
					currNode=fln;
					document.push(fln);
					curr=lex.getNextToken();
					continue;
				}
				if(curr.getType()==TokenType.ECHO) {
					EchoNode en = parseEcho();
					currNode.addChildNode(en);
					curr=lex.getNextToken();
					continue;
				}
				if(curr.getType()==TokenType.VAR&&"END".equalsIgnoreCase((String) curr.getValue())) {
					try{
						document.pop();
						currNode=(Node) document.peek();
						lex.getNextToken();
						curr=lex.getNextToken();
						continue;
					}catch(EmptyStackException e) {
						throw new SmartScriptParserException("Given document contains more END tags than non-empty tags");
					}
				}else {
					throw new SmartScriptParserException("Token in tag is of wrong type.");
				}


				
			default:
				throw new SmartScriptParserException("Error parsing token");
			}
			
		}
	}
	private ForLoopNode parseFor() {
		ForLoopNode retVal;
		ElementVariable variable;
		Element startExpression;
		Element endExpression;
		Token t = lex.getNextToken();
		
		if(t.getType()==TokenType.VAR) {
			variable=new ElementVariable((String) t.getValue());
		}else {
			throw new SmartScriptParserException();
		}
		startExpression = getNextElementForExpression(true);
		endExpression=getNextElementForExpression(true);
		t=lex.getNextToken();
		if(t.getType()==TokenType.TAG) {
			retVal= new ForLoopNode(variable, startExpression, endExpression, null);
		}else if(t.getType()==TokenType.VAR||t.getType()==TokenType.STRING||t.getType()==TokenType.DOUBLE||t.getType()==TokenType.INT){
			retVal= new ForLoopNode(variable, startExpression, endExpression,getNextElementForExpression(false) );
		}else {
			throw new SmartScriptParserException(); 
		}
		t=lex.getNextToken();
		if(t.getType()!=TokenType.TAG)throw new SmartScriptParserException();
		return retVal;
		
	}
	private EchoNode parseEcho() {
		Token t = lex.getNextToken();
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		while(t.getType()!=TokenType.TAG) {
			switch(t.getType()) {
			case VAR:
				elements.add(new ElementVariable((String) t.getValue()));
				break;
			case STRING:
				elements.add(new ElementString((String) t.getValue()));
				break;
			case DOUBLE:
				elements.add(new ElementConstantDouble((double) t.getValue()));
				break;
			case INT:
				elements.add(new ElementConstantInteger((int) t.getValue()));
				break;
			case FUNC:
				elements.add(new ElementFunction((String)t.getValue()));
				break;
			case OPERATOR:
				elements.add(new ElementOperator(String.valueOf(t.getValue())));
				break;
			default:
				throw new SmartScriptParserException();
			}
			t=lex.getNextToken();
		}
		
		Element[] temp = new Element[elements.size()];
		for(int i=0;i<elements.size();i++)temp[i]=(Element) elements.get(i);
		EchoNode en = new EchoNode(temp);
		return en;
	}
	private Element getNextElementForExpression(boolean getNext) {
		Token t;
		if(getNext) {
			t = lex.getNextToken();
		}else {
			t = lex.getToken();
		}
		
		switch(t.getType()) {
			case VAR:
				return new ElementVariable((String) t.getValue());
			case STRING:
				return new ElementString((String) t.getValue());
			case DOUBLE:
				return new ElementConstantDouble((double) t.getValue());
			case INT:
				return new ElementConstantInteger((int) t.getValue());
			default:
				throw new SmartScriptParserException();
		}
	}
	/**Getter that returns the document node instance of this object
	 * @return DocumentNode of this object
	 */
	public DocumentNode getDocumentNode() {
		try {
			return (DocumentNode) document.peek();
		}catch(Exception e) {
			throw new SmartScriptParserException(e);
		}
		
	}

}
