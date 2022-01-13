package hr.fer.oprpp1.hw04.db;

/**Class that represents Conditional expression, used for executing queries
 * @author Fran Kristijan Jelenčić
 *
 */
public class ConditionalExpression {
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	public ConditionalExpression(IFieldValueGetter fieldValueGetters,String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldGetter=fieldValueGetters;
		this.stringLiteral=stringLiteral;
		this.comparisonOperator=comparisonOperator;
	}
	/**Getter for fieldGetter
	 * @return IFieldValueGetter of this object
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	/**Getter for stringLiteral
	 * @return StringLiteral of this object
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
	/**Getter for comparisonOperator
	 * @return IComparisonOperator of this object
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
}
