package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

/**Class that is an implementation of Collection by using arrays for storage of elements
 * @author frank
 *
 */

public class ArrayIndexedCollection extends Collection {
	private final static int INITIALCAPACITY = 16; 
	private Object[] elements;
	private int size;
	private int capacity;
	

	
	/**Constructor that accepts an int that will set the capacity of the collection
	 * @param capacity the capacity of this new collection
	 * @throws IllegalArgumentException if the array capacity is set to less than 1
	 */
	public ArrayIndexedCollection(int capacity) {
		if(capacity<1) {
			throw new IllegalArgumentException("Array capacity can not be less than 1");
		}
		this.capacity = capacity;
		elements = new Object[capacity];
	}
	
	/**
	 * Constructor without parameters, sets the inital capacity to 16.
	 */
	public ArrayIndexedCollection() {
		this(INITIALCAPACITY);
	}
	/**Constructor that accepts another collection, adds all the elements of the other collection into this new one
	 * @param other the collection to copy
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other,other.size()<INITIALCAPACITY?INITIALCAPACITY:other.size());

	}
	/**Constructor that accepts another collection, adds all the elements of the other collection into this one
	 * while also setting size to the given int
	 * @param other the collection to copy
	 * @param capacity size of this new collection
	 */
	public ArrayIndexedCollection(Collection other,int capacity) {
		this(capacity);
		if(other==null)throw new NullPointerException("Given collection cannot be null");
		this.addAll(other);
	}
	
	/**
	 *Returns the current size of the collection
	 */
	public int size() {
		return size;
	}
	/**
	 * Copies the current collection and doubles its capacity filling the empty space with null elements
	 */
	private void realloc() {
		elements = Arrays.copyOf(elements, 2*capacity);
		capacity=2*capacity;
	}
	

	public void forEach(Processor processor) {
		for(Object o:elements) {
			if(o==null) break;
			processor.process(o);
		}
	}

	public Object[] toArray() {
		return elements;
	}
	/** Method returns true if collection contains object otherwise false
	 * @param value the object to be checked whether it exists in the collection
	 *@throws NullPointerException if object given is null
	 */
	public boolean contains(Object value) {
		if(value==null)throw new NullPointerException("Object given was null");
		for(int i = 0; i<size && get(i)!=null; i++) {
			if(get(i).equals(value))
				return true;
		}
		return false;
	}
	
	/**
	 * Method that appends the given object into the collection
	 *@param value Object that is to be added
	 *@throws NullPointerException is thrown when the given parameter is null
	 */
	public void add(Object value) {
		if(value==null) throw new NullPointerException("Null value is not allowed to be inserted");
		if(size>=capacity) {
			realloc();
		}
		elements[size++] = value;
	}
	
	/**
	 * @param index from which index to get the element
	 * @return the object at the given index
	 * @throws IndexOutOfBoundsException if the index is not within the correct bounds
	 */
	public Object get(int index) {
		if(index<0||index>size-1) throw new IndexOutOfBoundsException("The given index was "+index+", indexes that are allowed are between 0 and  the size of collection which is in this case "+(size-1));
		return elements[index];
	}
	
	/**
	 * Sets all elements of the collection to null so that they may be garbage collected, also sets the size to 0
	 */
	public void clear() {
		for(int i = 0;i<size;i++) {
			elements[i]=null;
		}
		size=0;
	}
	
	/**
	 * Inserts the given object at the given position, if the size is too small the capacity is doubled
	 * @param value object that is to be inserted
	 * @param position where the object is to be inserted (index)
	 * @throws NullPointerException if object to be inserted is null
	 * @throws IndexOutOfBoundsException if the given index is not less than 0 or greater that size of collection
	 */
	public void insert(Object value, int position) {
		if(value==null) throw new NullPointerException("Object to be inserted cannot be null");
		if(position<0||position>size) throw new IndexOutOfBoundsException("The given index was "+position+", indexes that are allowed are between 0 and  "+size);
		if(size+1>capacity) realloc();
		
		for(int i = size;i>position;i--) 
			elements[i]=elements[i-1];//move everything to the right, starting from the end
		elements[position] = value;
		size++;
	}
	
	/**
	 * Returns the index of the given object, if the object does not exist or is <code>null</code> the method returns -1
	 * @param value the object that is 
	 * @return if the value is found returns its index, otherwise -1
	 */
	public int indexOf(Object value) {
		if(value==null)return -1;
		for(int i = 0;i<size;i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes the element at given index
	 * @param index index of the element to be removed
	 * @throws IndexOutOfBoundsException if the given index is an element that doesn't exist
	 */
	public void remove(int index) {
		if(index<0||index>size-1) throw new IndexOutOfBoundsException("The given index was "+index+", indexes that are allowed are between 0 and  "+(size-1));
		for(int i=index;i<size&&elements[i]!=null;i++) {
			elements[i]=elements[i+1];
		}
		size--;
	}
}
