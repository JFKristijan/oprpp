package hr.fer.oprpp1.custom.collections;

/**Interface used to get elements of a collection
 * @author Fran Kristijan Jelenčić
 *
 */
public interface ElementsGetter<T> {
	/**Checks if the collection has a next element to be returned
	 * @return true if collection has more elements to return, false otherwise
	 */
	public boolean hasNextElement();
	/**Gets the next element of the list
	 * @return object that is the next element in the list
	 */
	public T getNextElement();
	/**Processes the remaining elements of the collection with the given processor
	 * @param p Processor that whose method process will be used to process objects of the collection
	 */
	public default void processRemaining(Processor<? super T> p) {
		while(hasNextElement())p.process(getNextElement());
	}
}
