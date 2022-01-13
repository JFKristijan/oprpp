package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**Class that represents an Echo node in a Document 
 * @author frank
 *
 */
public class EchoNode extends Node {
	private Element[] elements;
	public EchoNode(Element[] elements) {
		this.elements=elements;
	}
	/**Method that returns all the elements of the this Node
	 * @return an array of elements of this EchoNode
	 */
	public Element[] getElements() {
		return elements;
	}
	/**Method that returns the string represntation of this Node
	 *@return String that is the string representation of this For loop in tag form 
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$=");
		for(int i = 0 ; i < elements.length ; i++) {
			sb.append(" "+elements[i].toString()+" ");
		}
		sb.append("$}");
		return sb.toString();
	}
	/**Indicates whether some other object is "equal to" this one. 
	 *@param obj object to check whether or not is equal
	 *@return true if objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof EchoNode))return false;
		EchoNode temp = (EchoNode) obj;
		if(this.elements.length!=temp.elements.length)return false;
		for(int i=0;i<elements.length;i++) {
			if(!elements[i].equals(temp.elements[i]))return false;
		}
		return true;
	}
	
}
