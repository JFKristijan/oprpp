package hr.fer.oprpp1.custom.collections;

/**Interface used for implementing collections
 * @author frank
 *
 */
public interface List extends Collection {
	/**Method that returns the object at the given index
	 * @param index index from which to return from
	 * @return Object at given index
	 */
	Object get(int index);
	
	/**Method that inserts given object at given position
	 * @param value
	 * @param position
	 */
	void insert(Object value, int position);
	/**Method return index of given object, -1 if the object does not exist in List
	 * @param value object from which to return the index of
	 * @return index of given object, -1 if it doesn't exist
	 */
	int indexOf(Object value);
	/**Method that removes element at given index
	 * @param index index from which to remove the element from
	 */
	void remove(int index);
}
