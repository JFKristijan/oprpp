package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


/** Class that represents a collection, implemented using a linked list
 * @author Fran Kristijan Jelenčić
 *
 */
public class LinkedListIndexedCollection<T> implements List<T> {
	/**
	 * Private class that is used as a linked list node
	 *
	 */
	private static class ListNode<T>{
		private ListNode<T> next;
		private ListNode<T> previous;
		private T value;

		public ListNode(T value) {
			this.value=value;
		}

		public ListNode<T> getNext() {
			return next;
		}
		public void setNext(ListNode<T> next) {
			this.next = next;
		}
		public ListNode<T> getPrevious() {
			return previous;
		}
		public void setPrevious(ListNode<T> previous) {
			this.previous = previous;
		}
		public T getValue() {
			return value;
		}



	}
	private static class ElementsGetterImpl<T> implements ElementsGetter<T>{
		private LinkedListIndexedCollection<T> ref;
		private ListNode<T> curr;
		private long savedModificationcount;
		public  ElementsGetterImpl(LinkedListIndexedCollection<T> ln) {
			ref=ln;
			curr=ref.first;
			savedModificationcount=ln.modificationCount;
		}
		@Override
		public boolean hasNextElement() {
			if(savedModificationcount!=ref.modificationCount)
				throw new ConcurrentModificationException();
			return curr!=null;

		}
		/**Gets the next element of the list
		 * @return object that is the next element in the list
		 * @throws ConcurrentModificationException if method is called after inserting, clearing or removing an object
		 */
		@Override
		public T getNextElement() {
			if(savedModificationcount!=ref.modificationCount)throw new ConcurrentModificationException();
			if(curr==null)throw new NoSuchElementException();
			T retval = curr.getValue();
			curr=curr.getNext();
			return retval;
		}

	}

	private int size;
	private ListNode<T> first;
	private ListNode<T> last;
	private long modificationCount;
	/**
	 * Empty construct that initialises first and last to null
	 */
	public LinkedListIndexedCollection() {
		first=null;
		last=null;
		modificationCount=0;
	}
	/**
	 * Constructor that creates a copies the given collection into the newly constructed collection
	 * @param other collection from which the elements are copied
	 * @throws NullPointerException if the collection passed in the parameter is <code>null</code>
	 */
	public LinkedListIndexedCollection(LinkedListIndexedCollection<T> other) {
		if(other==null) throw new NullPointerException("Given collection is a null object");
		//		first = new ListNode<T>(other.first);
		//		ListNode<T> curr = other.first; // iterates through other collection
		//		ListNode<T> newNode=first; 
		//		while(curr.hasNext()) {
		//			ListNode<T> temp = new ListNode<T>(curr.getNext());
		//			newNode.setNext(temp);
		//			temp.setPrevious(newNode);
		//			newNode=temp;
		//			curr=curr.getNext();
		//		}
		//		last=newNode;
		//		size=other.size;
		other.forEach(this::add);
	}
	/**
	 * Constructor that creates a copies the given collection into the newly constructed collection
	 * @param other collection from which the elements are copied
	 */
	public LinkedListIndexedCollection(ArrayIndexedCollection<T> other) {
		//		 LinkedListIndexedCollection<T> curr = this;
		////		 class AdderProcessor implements Processor{
		////			 public void process(Object value) {
		////				 curr.add(value);
		////			 }
		////		 }
		other.forEach(this::add);

	}

	/**
	 * Method that adds the given object to the end of the list
	 * @param value object to be added to the list
	 * @throws NullPointerException if the object to be added is <code>null</code>
	 */
	public void add(T value) {
		if(value==null) throw new NullPointerException("Object to be added is not allowed to be null");
		ListNode<T> listNodeNew = new ListNode<T>(value);
		if(size==0) { 
			first = listNodeNew;
			last = listNodeNew;
		}else {
			last.setNext(listNodeNew);
			listNodeNew.setPrevious(last);
			last=listNodeNew;
		}
		size++;
		modificationCount++;
	}

	/**
	 * Helper method used to get Node object at given index
	 * @param index from which to get the element
	 * @return ListNode element at given index
	 */
	private ListNode<T> getHelper(int index) {
		ListNode<T> temp;
		int sizeNeeded;
		boolean whichWay = false;// false for iterating backwards, true for from the beginning
		if(index>size/2) {
			temp=last;
			sizeNeeded=size-index-1;
		}else {
			temp=first;
			sizeNeeded = index;
			whichWay=true;
		}
		for(int counter=0;counter<sizeNeeded;counter++) {
			temp=whichWay?temp.getNext():temp.getPrevious();
		}
		return temp;
	}

	/**
	 * Method that returns value of the collection at the given index
	 * @param index from which to get the element from
	 * @return value of the collection at given index
	 * @throws IndexOutOfBoundsException if the given index is not between 0 and size-1
	 */
	public T get(int index) {
		if(index<0||index>size-1) throw new IndexOutOfBoundsException("The given index was "+index+", indexes that are allowed are between 0 and  the size of collection which is in this case "+(size-1));
		ListNode<T> temp = getHelper(index);
		return temp.getValue();
	}

	/**
	 * Clears the collection of items, size is set to 0
	 */
	public void clear() {
		ListNode<T> temp= first;

//		for(int i=0;i<size;i++) {
//			temp.value = null;
//			temp = temp.getNext();
//		}
		first=null;
		last=null;

		size=0;
		modificationCount++;
	}

	/**
	 * Inserts the given object at the given position
	 * @param value to be inserted
	 * @param position at which index to insert the value at
	 * @throws NullPointerException if the given object is null
	 * @throws IndexOutOfBoundsException if the position of insertion is not between 0 and size
	 */
	public void insert(T value, int position) {
		if(value==null) throw new NullPointerException("Object to be inserted cannot be null");
		if(position<0||position>size) throw new IndexOutOfBoundsException("The given index was "+position+", indexes that are allowed are between 0 and  "+size);
		ListNode<T> temp = getHelper(position);
		ListNode<T> insert = new ListNode<T>(value);

		if(size==0||position==size) {
			add(value);
			return;
		}
		if(position==0) {
			first=insert;
			insert.setNext(temp);
			temp.setPrevious(insert);
		}else {
			temp.getPrevious().setNext(insert);
			insert.setPrevious(temp.getPrevious());
			insert.setNext(temp);
			temp.setPrevious(insert);
		}
		size++;
		modificationCount++;
	}
	/**
	 * Returns the index of the value given, if it does not exist within the collection returns -1
	 * @param value whose index is needed
	 * @return index at which the object with the given value is
	 */
	public int indexOf(Object value) {
		if(value==null)return -1;
		for(int i=0;i<size;i++) if(value.equals(get(i))) return i;
		return -1;
	}



	/**
	 * Removes the element at the specified index from the collection. Elements that was previously at location index+1 is now on location index, etc.
	 * @param index at which index to remove the element at
	 * @throws IndexOutOfBoundsException if the given index is not between 0 and size-1
	 */
	public void remove(int index) {
		if(index<0||index>size-1) throw new IndexOutOfBoundsException("The given index was "+index+", indexes that are allowed are between 0 and  "+(size-1));
		ListNode<T> temp = getHelper(index);
		if(index==0&&size-1==0) {
			clear();
			return;
		}
		if(temp.getPrevious()!=null) {
			temp.getPrevious().setNext(temp.getNext());
		}
		if(temp.getNext()!=null) {
			temp.getNext().setPrevious(temp.getPrevious());
		}
		if(index==0) {
			first=temp.getNext();
		}
		if(index==size-1) {
			last=temp.getPrevious();
		}
		size--;
		modificationCount++;
	}

	public Object[] toArray() {
		ListNode<T> temp = first;
		Object[] retval = new Object[size];
		for(int i=0;i<size;i++) {
			retval[i]=temp.getValue();
			temp = temp.getNext();
		}
		return retval;
	}
	/**Method that checks whether given Object is in this list using equals method
	 *@param value the value that is checked whether it exists in the list
	 *@return true if the value exists in the list, false otherwise
	 */

	public boolean contains(Object value) {
		ListNode<T> temp = first;
		for(int i=0;i<size;i++) {
			if(temp.getValue().equals(value)) return true;
			temp = temp.getNext();
		}
		return false;

	}

	/**Method that returns the current size of this colection
	 * @return int size the size of the collection
	 *
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Removes the object from the collection. Elements that was previously at location index+1 is now on location index, etc.
	 * @param value object to remove from the collection
	 * @throws NullPointerException if the given object is null
	 * @return true if removal is successful, false otherwise
	 */
	@Override
	public boolean remove(Object value) {
		if(value==null)throw new NullPointerException("Given object was null");
		ListNode<T> temp = first;
		for(int i=0;i<size;i++) {
			if(temp.getValue().equals(value)) {
				remove(i);
				return true;
			};
			temp = temp.getNext();
		}
		return false;
	}
	/**Creates an implementation of an element getter used for iterating over the collection
	 *
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetterImpl<T>(this);
	}
}
