package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**Class that is an implementation of IFilter interface
 * @author Fran Kristijan Jelenčić
 *
 */
public class QueryFilter implements IFilter {
	private List<ConditionalExpression> listExpressions;
	public QueryFilter(List<ConditionalExpression> list) {
		listExpressions=list;
	}
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression e:listExpressions) {
			if(!e.getComparisonOperator().satisfied(
					e.getFieldGetter().get(record), 
					e.getStringLiteral() 
					))return false;
		}
		return true;
	}

}
