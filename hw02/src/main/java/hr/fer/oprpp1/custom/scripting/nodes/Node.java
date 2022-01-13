package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**Abstract class that represents a Node
 * @author frank
 *
 */
public abstract class Node {
	protected ArrayIndexedCollection children;
	
	/**Method that adds given node to this one as a child
	 * @param child Node to be added
	 * @throws NullPointerException if the given node is null
	 */
	public void addChildNode(Node child) {
		if(children==null)children = new ArrayIndexedCollection();
		if(child==null)throw new NullPointerException();
		children.add(child);
	}
	/**Method that returns the number of children this node has
	 * @return int that represents the number of children of this node
	 */
	public  int numberOfChildren() {
		if(children==null)return 0;
		return children.size();
	}
	/**Method that returns a child of this node at the given index
	 * @param index of the child to be returned
	 * @return Node that is the child at given index
	 * @throws NullPointerException if the node does not have any children
	 */
	public  Node getChild(int index) {
		if(children==null)throw new NullPointerException("Node has no children and as such given index is invalid");
		return (Node) children.get(index);
	}

		
}
