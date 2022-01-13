package hr.fer.oprpp1.custom.scripting.elems;

/**Class that represents an element of the type Constant Double
 * @author frank
 *
 */
public class ElementConstantDouble extends Element {
	public double value;
	public ElementConstantDouble(double value) {
		this.value=value;
	}
	@Override
	public String asText() {
		return Double.toString(value);
	}
	/**Returns the value of this Element
	 * @return double value that represents this elements value
	 */
	public double getValue() {
		return value;
	}
	
	/**Returns the string representation of this Element
	 *@return String that is the string represntation of the element
	 */
	public String toString() {
		return Double.toString(value);
	}
	/**Method that returns true if the given object is equal to this object
	 *@param obj the other object to compare this element to
	 *@return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ElementConstantDouble))return false;
		ElementConstantDouble temp = (ElementConstantDouble) obj;
		return this.value==temp.value;
	}
}
