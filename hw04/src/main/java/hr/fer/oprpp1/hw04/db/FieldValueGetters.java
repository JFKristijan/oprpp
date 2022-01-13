package hr.fer.oprpp1.hw04.db;

/**Class that represents value getters as static methods
 * @author Fran Kristijan Jelenčić
 *
 */
public class FieldValueGetters {
	public static final IFieldValueGetter FIRST_NAME = r -> r.getFirstName();
	public static final IFieldValueGetter LAST_NAME = r -> r.getLastName();
	public static final IFieldValueGetter JMBAG = r -> r.getJmbag();
}
