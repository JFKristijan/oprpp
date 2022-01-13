package hr.fer.oprpp1.custom.collections;

/**Class that represents a collection of objects
 * @author Fran Kristijan Jelencic
 *
 */
public class Collection {

	/**
	 * Class that is a collection of objects
	 */
	protected Collection() {}
	
	/**Returns whether the collection is empty
	 * @return boolean 
	 */
	public boolean isEmpty() {
		return size()==0;
	}
	
	/**Returns the number of currently stored objects in this collection.
	 * @return integer size 
	 */
	public int size() {
		return 0;
	}
	
	/**Adds the given object into this collection
	 * @param value object to be added
	 */
	public void add(Object value) {}
	
	 /**Method that returns true only if the collection contains the given value,
	  *  as determined by equals method
	 * @param value object to be checked if it is contained in the collection
	 * @return boolean true if object exists otherwise false
	 */
	public boolean contains(Object value) {
		 return false;
	 }
	 
	 /**Removes one occurrence of given object as determined by equals method
	 * @param value object to be removes
	 * @return true if the collection contains given value and removes it, otherwise false
	 */
	public boolean remove(Object value) {
		 return false;
	 }
	 
	 /**Allocates new array with size equal to the size of this collection, 
	  * fills it with collection content and returns the array
	 * @return array with objects of the given collection
	 */
	public Object[] toArray() {
		 throw new UnsupportedOperationException();
	 }
	 
	 /** Method calls processor.processor() for each element of this collection
	  * @param processor processor class that is to be used for processing
	 */
	public void forEach(Processor processor) {}
	 

	/**Method adds all elements of the given collection into this collection. 
	 * The other collection remains unchanged.
	 * @param other
	 */
	public void addAll(Collection other) {
		Collection current = this;
		 class LocalProcessor extends Processor{
			public void process(Object value) {
				current.add(value);
			 }
		 }
		 other.forEach(new LocalProcessor());
		 
	 }
	 
	 /**
	 * Clears the given collection of elements
	 */
	void clear() {}
}
