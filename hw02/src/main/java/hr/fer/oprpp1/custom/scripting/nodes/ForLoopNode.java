package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**Class that is the ForLoopNode of a Document
 * @author frank
 *
 */
public class ForLoopNode extends Node {
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	public ForLoopNode(ElementVariable var,Element startExpression,Element endExpression,Element stepExpression) {
		variable=var;
		this.startExpression=startExpression;
		this.endExpression=endExpression;
		this.stepExpression=stepExpression;
	}
	/**Method that returns the variable of the this ForLoopNode object
	 * @return ElementVariable object 
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	/**Method that returns the start expression of this ForLoopNode object
	 * @return Element that is the start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	/**Method that returns the end expression of this ForLoopNode object
	 * @returnElement that is the end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	/**Method that returns the step expression of this ForLoopNode object
	 * @return Element that is the step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	/**Returns the contents of the node in string form
	 *@return String that is the string representation of this For loop in tag form 
	 */
	public String toString() {
		return "{$ FOR "+variable+" "+startExpression+" "+endExpression+" "+(stepExpression==null?"":stepExpression)+" $}";
	}
	/**Indicates whether some other object is "equal to" this one. 
	 *@param obj object to check whether or not is equal
	 *@return true if objects are equal, false otherwise
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof ForLoopNode))return false;
		ForLoopNode obj1 = (ForLoopNode) obj;
		return variable.equals(obj1.variable)
				&& startExpression.equals(obj1.startExpression) 
				&& endExpression.equals(obj1.endExpression)
				&& stepExpression.equals(obj1.stepExpression);
	}
	
}
