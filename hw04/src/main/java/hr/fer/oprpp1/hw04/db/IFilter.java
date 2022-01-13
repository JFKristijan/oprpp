package hr.fer.oprpp1.hw04.db;

/**Class used as a filter
 * @author Fran Kristijan Jelenčić
 *
 */
public interface IFilter {
	
	/**Method returns true if StudentRecord satisfies conditions of the filter
	 * @param record the record to test
	 * @return true if it satisfies the condition of this filter, false otherwise
	 */
	public boolean accepts(StudentRecord record);
}
