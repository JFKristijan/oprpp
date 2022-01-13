package hr.fer.oprpp1.custom.scripting.elems;

/**Class that represents an element of the type Function
 * @author frank
 *
 */
public class ElementFunction extends Element {
	private String name;
	public ElementFunction(String name) {
		this.name = name;
	}
	@Override
	public String asText() {
		return "@"+name;
	}
	/**Returns the name of this Element
	 * @return String value that represents this elements name
	 */
	public String getName() {
		return name;
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
		if(!(obj instanceof ElementFunction))return false;
		ElementFunction temp = (ElementFunction) obj;
		return this.name.equals(temp.name);
	}
}
