package hr.fer.oprpp1.custom.scripting.elems;

/**Class that represents an element of the type Constant Integer
 * @author frank
 *
 */
public class ElementConstantInteger extends Element {
	private int value;
	public ElementConstantInteger(int value) {
		this.value=value;
	}
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	/**Returns the value of this Element
	 * @return integer value that represents this elements value
	 */
	public int getValue() {
		return value;
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
		if(!(obj instanceof ElementConstantInteger))return false;
		ElementConstantInteger temp = (ElementConstantInteger) obj;
		return this.value==temp.value;
	}
}
