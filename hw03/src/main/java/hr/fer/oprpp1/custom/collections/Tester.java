package hr.fer.oprpp1.custom.collections;

/**Interface used for testing objects
 * @author frank
 *
 */
public interface Tester<T> {
	/**Method that tests the given object, result depends on implementation
	 * @param obj to be tested
	 * @return true if test is valid, false otherwise
	 */
	boolean test(T obj);
}
