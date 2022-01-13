package hr.fer.oprpp1.hw04.db;

/**Interface used for getting field values from a student record
 * @author Fran Kristijan Jelenčić
 *
 */
public interface IFieldValueGetter {
	
	 /**Method that returns the field value from record
	 * @param record to get field value from
	 * @return String that is the value of the record
	 */
	public String get(StudentRecord record);
}
