package hr.fer.oprpp1.custom.scripting.parser;

/** The class is an exception that is created when an empty stack is attempted to be popped
 * @author frank
 *
 */
public class EmptyStackException extends RuntimeException {
	public EmptyStackException(String s) {
		super(s);
	}
	public EmptyStackException() {
		super();
	}

}
