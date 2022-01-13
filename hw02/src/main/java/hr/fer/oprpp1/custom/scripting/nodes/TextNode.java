package hr.fer.oprpp1.custom.scripting.nodes;

/**Class that is a TextNode of a Document
 * @author frank
 *
 */
public class TextNode extends Node {
	private String text;

	public TextNode(String text) {
		this.text=text;
	}
	/**Method that returns the text of this TextNode
	 * @return
	 */
	public String getText() {
		return text;
	}
	/**Returns the text of this TextNode as a string
	 *@return String that is the string representation of this TextNode 
	 */
	public String toString() {
		return text;
	}
	/**Indicates whether some other object is "equal to" this one. 
	 *@param obj object to check whether or not is equal
	 *@return true if objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TextNode))return false;
		TextNode temp = (TextNode) obj;
		return text.equals(temp.text);
	}
}
	
