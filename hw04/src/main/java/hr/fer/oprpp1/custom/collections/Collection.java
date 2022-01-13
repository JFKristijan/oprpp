package hr.fer.oprpp1.custom.collections;

/**Class that represents a collection of objects
 * @author Fran Kristijan Jelencic
 *
 */
public interface Collection<T> {

	
	/**Returns whether the collection is empty
	 * @return boolean 
	 */
	public default boolean isEmpty() {
		return size()==0;
	}
	
	/**Returns the number of currently stored objects in this collection.
	 * @return integer size 
	 */
	public abstract int size();
	
	/**Adds the given object into this collection
	 * @param value object to be added
	 */
	public abstract void add(T value);
	
	 /**Method that returns true only if the collection contains the given value,
	  *  as determined by equals method
	 * @param value object to be checked if it is contained in the collection
	 * @return boolean true if object exists otherwise false
	 */
	public abstract boolean contains(Object value);
	 /**Removes one occurrence of given object as determined by equals method
	 * @param value object to be removes
	 * @return true if the collection contains given value and removes it, otherwise false
	 */
	public abstract boolean remove(Object value);
	 
	 /**Allocates new array with size equal to the size of this collection, 
	  * fills it with collection content and returns the array
	 * @return array with objects of the given collection
	 */
	public abstract Object[] toArray();
	 
	 /** Method calls processor.processor() for each element of this collection
	  * @param processor processor class that is to be used for processing
	 */
	public default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> eg = this.createElementsGetter();
		while(eg.hasNextElement()) processor.process(eg.getNextElement());
	}
	 

	/**Method adds all elements of the given collection into this collection. 
	 * The other collection remains unchanged.
	 * @param other
	 */
	public default void addAll(Collection<? extends T> other) {
		 other.forEach(this::add);
	 }
	 
	 /**
	 * Clears the given collection of elements
	 * @return 
	 */
	public abstract void clear();
	
	public abstract ElementsGetter<T> createElementsGetter();
	
	/**Adds all elements of given collection into this one that satisfy tester conditions
	 * @param col Collection to add objects from into this one
	 * @param tester tester to determine validity of given object
	 */
	public default void addAllSatisfying(Collection<? extends T> col,Tester<? super T> tester) {
		ElementsGetter<? extends T> eg = col.createElementsGetter();
		while(eg.hasNextElement()) {
			T obj = eg.getNextElement();
			if(tester.test(obj))this.add(obj);
		}
			
	}
}
