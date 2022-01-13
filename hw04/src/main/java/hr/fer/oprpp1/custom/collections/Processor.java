package hr.fer.oprpp1.custom.collections;

/**Interface used for processing objects
 * @author Fran Kristijan Jelenčić
 *
 */
public interface Processor<T> {
	
	/**Processes an object
	 * @param value to be processed
	 */
	public void process(T value);
}
