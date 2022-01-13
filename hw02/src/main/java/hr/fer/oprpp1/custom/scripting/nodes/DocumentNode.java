package hr.fer.oprpp1.custom.scripting.nodes;



/**Class that represents a Document
 * @author frank
 *
 */
public class DocumentNode extends Node {
	public DocumentNode() {}
	/**Returns the contents of the node in string form, visiting all children and their childer and so on...
	 *@return String that is the string representation of this Document 
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < numberOfChildren() ; i++) {
			doDepth(getChild(i),sb);
		}
		return sb.toString();
	}
	private void doDepth(Node n,StringBuilder sb) {
		sb.append(n.toString());
		for(int i = 0 ; i < n.numberOfChildren() ; i++) {
			doDepth(n.getChild(i),sb);
		}
		if(n instanceof ForLoopNode)sb.append("{$END$}");
		
	}


	/**Indicates whether some other object is "equal to" this one. 
	 *@param obj object to check whether or not is equal
	 *@return true if objects are equal, false otherwise
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof DocumentNode))return false;
		DocumentNode temp = (DocumentNode) obj;
		if(temp.numberOfChildren()!=this.numberOfChildren())return false;
		for(int i = 0; i < numberOfChildren() ; i++) {
			if(!doDepthEquals(getChild(i),temp.getChild(i)))return false;
		}
		return true;
	}
	private boolean doDepthEquals(Node n,Node other) {
		if(!n.equals(other))return false;
		if(n.numberOfChildren()!=other.numberOfChildren())return false;
		for(int i = 0 ; i < n.numberOfChildren() ; i++) {
			if(!doDepthEquals(n.getChild(i),other.getChild(i)))return false;
		}
		return true;		
	}
	
	
	
}
