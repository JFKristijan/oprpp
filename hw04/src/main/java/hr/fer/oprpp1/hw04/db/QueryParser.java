package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**Class that is used to parse queries given by the user
 * @author Fran Kristijan Jelenčić
 *
 */
public class QueryParser {
	QueryLexer lex;
	ArrayList<ConditionalExpression> queries;
	
	/**Constructor that immediately parses given query
	 * @param query to parse
	 * 
	 */
	public QueryParser(String query) {
		lex = new QueryLexer(query);
		queries = new ArrayList<ConditionalExpression>();
		try {
		parse();
		}catch(Exception e) {
			throw new IllegalArgumentException("An error occurred during parsing " +e);
		}
	}
	private void parse() {
		while(lex.hasNextToken()) {
			IFieldValueGetter fieldValueGetters = null;
			String stringLiteral ="";
			IComparisonOperator comparisonOperator = null;
			String s = lex.getNextToken();
			fieldValueGetters = switch(s) {
			case "jmbag" ->	  FieldValueGetters.JMBAG;
			case "firstName" -> FieldValueGetters.FIRST_NAME;
			case "lastName" -> FieldValueGetters.LAST_NAME;
			default -> throw new IllegalArgumentException("unable to parse fieldValueGetter query");};
			s = lex.getNextToken();
			comparisonOperator = switch(s.toUpperCase()) {
			case ">" ->	  ComparisonOperators.GREATER;
			case ">=" -> ComparisonOperators.GREATER_OR_EQUALS;
			case "<" ->	  ComparisonOperators.LESS;
			case "<=" -> ComparisonOperators.LESS_OR_EQUALS;
			case "=" -> ComparisonOperators.EQUALS;
			case "!=" -> ComparisonOperators.NOT_EQUALS;
			case "LIKE" -> ComparisonOperators.LIKE;
			default -> throw new IllegalArgumentException("unable to parse comparisonOperator query");
			};
			stringLiteral = lex.getNextToken().replace("\"", "");
			ConditionalExpression c = new ConditionalExpression(fieldValueGetters, stringLiteral, comparisonOperator);
			queries.add(c);
			if(lex.hasNextToken()) {
				String str = lex.getNextToken();
				if(!str.toLowerCase().equals("and"))throw new IllegalArgumentException("unable to parse query duo to syntax error");
			}
		}
	}
	
	/**Checks if query is direct
	 * A direct query is jmbag = "something"
	 * @return true if query is direct, false otherwise
	 */
	public boolean isDirectQuery() {
		ConditionalExpression expr = queries.get(0);
		return queries.size()==1&&expr.getFieldGetter().equals(FieldValueGetters.JMBAG)&&expr.getComparisonOperator().equals(ComparisonOperators.EQUALS);
	}
	
	/**If query is direct returns the queried jmbag
	 * @return jmbag that is queried
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery())throw new IllegalStateException("Query is not direct.");
		return  queries.get(0).getStringLiteral();
	}
	
	/**Getter for List of queried expressions
	 * @return List<ConditionalExpression> containing all queries from the given string in constructor
	 */
	public List<ConditionalExpression> getQuery(){
		return queries;
	}
}
