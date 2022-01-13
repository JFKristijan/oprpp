package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * @author Fran Kristijan Jelenčić
 *Class that is used for keeping context when drawing
 */
public class Context {
	ObjectStack<TurtleState> stack= new ObjectStack<TurtleState>();
	
	/**Method returns stack from top of stack
	 * @return TurtleState current state on top of stack
	 */
	public TurtleState getCurrentState() {return stack.peek();}
	
	/**Method pushes given state to stack
	 * @param state to push to stack
	 */
	public void pushState(TurtleState state) {stack.push(state);}
	
	
	/**Method pops a state from stack
	 */
	public void popState() {stack.pop();}
}
