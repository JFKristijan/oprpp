package hr.fer.oprpp1.custom.scripting.elems;

/**Class that represents an element of the type String
 * @author frank
 *
 */
public class ElementString extends Element {
	private String value;
	public ElementString(String value) {
		this.value=value;
	}
	@Override
	public String asText() {
		return "\""+value.replace("\"", "\\\"")+"\"";
	}
	public String getValue() {
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
		if(!(obj instanceof ElementString))return false;
		ElementString temp = (ElementString) obj;
		return this.value.equals(temp.value);
	}
}
