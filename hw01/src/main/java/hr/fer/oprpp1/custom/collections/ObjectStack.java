package hr.fer.oprpp1.custom.collections;

/**Class that is used to achieve a Stack like structure
 * @author frank
 *
 */
public class ObjectStack {
	private ArrayIndexedCollection fakeStack;

	public ObjectStack() {
		fakeStack = new ArrayIndexedCollection();
	}
	
	/**
	 * @return true if stack is empty, false if not
	 */
	public boolean isEmpty() {
		return fakeStack.isEmpty();
	}
	
	/**
	 * @return the current size of the stack
	 */
	public int size() {
		return fakeStack.size();
	}
	
	/**Pushes given value on the stack
	 * @param value to be pushed
	 * @throws NullPointerException when null value is given to be pushed
	 */
	public void push(Object value) {
		if(value==null) throw new NullPointerException("Null value must not be allowed to be placed on the stack");
		fakeStack.add(value);
	}
	
	/**
	 * Clears all items from the stack
	 */
	public void clear() {
		fakeStack.clear();
	}
	
	/** Removes last value pushed on stack and returns it
	 * @return last value pushed
	 * @throws EmptyStackException if the method is called on an empty stack
	 */
	public Object pop() {
		if(fakeStack.size()==0)throw new EmptyStackException("Attempted to pop from empty stack");
		int index = fakeStack.size()-1;
		Object retval = fakeStack.get(index);
		fakeStack.remove(index);
		return retval;
	}
	
	/**
	 * Returns last element placed on stack but does not delete it from stack
	 * @return last element placed on stack
	 * @throws EmptyStackException if the method is called on an empty stack
	 */
	public Object peek() {
		if(fakeStack.size()==0)throw new EmptyStackException();
		return fakeStack.get(fakeStack.size()-1);
	}
	
	
}
