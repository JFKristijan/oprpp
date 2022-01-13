package hr.fer.oprpp1.custom.scripting.elems;

/**Class that represents an element of the type Operator
 * @author frank
 *
 */
public class ElementOperator extends Element {
	private String symbol;
	public ElementOperator(String symbol) {
		this.symbol=symbol;
	}
	@Override
	public String asText() {
		return symbol;
	}
	/**Returns the name of this Element
	 * @return String value that represents this elements name
	 */
	public String getName() {
		return symbol;
	}
	/**Returns the string representation of this Element
	 *@return String that is the string represntation of the element
	 */
	public String toString() {
		return asText();
	}
	/**Method that returns true if the given object is equal to this element
	 *@param obj the other object to compare this element to
	 *@return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementOperator))return false;
		ElementOperator temp = (ElementOperator) obj;
		return this.symbol.equals(temp.symbol);
	}
}
