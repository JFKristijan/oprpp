package hr.fer.oprpp1.hw04.db;

/**Interface used for comparing Strings
 * @author Fran Kristijan Jelenčić
 *
 */
public interface IComparisonOperator {
	
	/**Method that checks whether the given strings satisfy criteria
	 * @param value1 first string to check
	 * @param value2 second string to check
	 * @return true if criteria is satisfied, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
