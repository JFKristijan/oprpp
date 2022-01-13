package hr.fer.oprpp1.custom.collections;



/** Class that represents a collection, implemented using a linked list
 * @author frank
 *
 */
public class LinkedListIndexedCollection extends Collection {
	/**
	 * Private class that is used as a linked list node
	 *
	 */
	private static class ListNode{
		private ListNode next;
		private ListNode previous;
		private Object value;
		
		public ListNode(ListNode el) {
			this.value=el.value;
		}
		public ListNode(Object value) {
			this.value=value;
		}
		
		public ListNode getNext() {
			return next;
		}
		public void setNext(ListNode next) {
			this.next = next;
		}
		public ListNode getPrevious() {
			return previous;
		}
		public void setPrevious(ListNode previous) {
			this.previous = previous;
		}
		public Object getValue() {
			return value;
		}
		public boolean hasNext() {
			return next!=null;
		}

		
		
	}
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * Empty construct that initialises first and last to null
	 */
	public LinkedListIndexedCollection() {
		first=null;
		last=null;
	}
	/**
	 * Constructor that creates a copies the given collection into the newly constructed collection
	 * @param other collection from which the elements are copied
	 * @throws NullPointerException if the collection passed in the parameter is <code>null</code>
	 */
	public LinkedListIndexedCollection(LinkedListIndexedCollection other) {
		if(other==null) throw new NullPointerException("Given collection is a null object");
		for(int i = 0 ; i<other.size();i++) {
			this.add(other.get(i));
		}
//		first = new ListNode(other.first);
//		ListNode curr = other.first; // iterates through other collection
//		ListNode newNode=first; 
//		while(curr.hasNext()) {
//			ListNode temp = new ListNode(curr.getNext());
//			newNode.setNext(temp);
//			temp.setPrevious(newNode);
//			newNode=temp;
//			curr=curr.getNext();
//		}
//		last=newNode;
//		size=other.size;
	}
	/**
	 * Constructor that creates a copies the given collection into the newly constructed collection
	 * @param other collection from which the elements are copied
	 */
	 public LinkedListIndexedCollection(ArrayIndexedCollection other) {
		 LinkedListIndexedCollection curr = this;
		 class AdderProcessor extends Processor{
			 public void process(Object value) {
				 curr.add(value);
			 }
		 }
		 other.forEach(new AdderProcessor());
		 
	 }
	
	/**
	 * Method that adds the given object to the end of the list
	 * @param value object to be added to the list
	 * @throws NullPointerException if the object to be added is <code>null</code>
	 */
	public void add(Object value) {
		if(value==null) throw new NullPointerException("Object to be added is not allowed to be null");
		ListNode listNodeNew = new ListNode(value);
		if(size==0) { 
			first = listNodeNew;
			last = listNodeNew;
		}else {
			last.setNext(listNodeNew);
			listNodeNew.setPrevious(last);
			last=listNodeNew;
		}
		size++;
	}
	
	public int size() {
		return size;
	}
	
	/**
	 * Helper method used to get Node object at given index
	 * @param index from which to get the element
	 * @return ListNode element at given index
	 */
	private ListNode getHelper(int index) {
		ListNode temp;
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
	public Object get(int index) {
		if(index<0||index>size-1) throw new IndexOutOfBoundsException("The given index was "+index+", indexes that are allowed are between 0 and  the size of collection which is in this case "+(size-1));
		ListNode temp = getHelper(index);
		return temp.getValue();
	}
	
	/**
	 * Clears the collection of items, size is set to 0
	 */
	public void clear() {
		ListNode temp= first;

		for(int i=0;i<size;i++) {
			temp.value = null;
			temp = temp.getNext();
		}
		first=null;
		last=null;
		
		size=0;
	}
	
	/**
	 * Inserts the given object at the given postion
	 * @param value to be inserted
	 * @param position at which index to insert the value at
	 * @throws NullPointerException if the given object is null
	 * @throws IndexOutOfBoundsException if the position of insertion is not between 0 and size
	 */
	public void insert(Object value, int position) {
		if(value==null) throw new NullPointerException("Object to be inserted cannot be null");
		if(position<0||position>size) throw new IndexOutOfBoundsException("The given index was "+position+", indexes that are allowed are between 0 and  "+size);
		ListNode temp = getHelper(position);
		ListNode insert = new ListNode(value);

		if(size==0||position==size) {
			add(value);
			return;
		}
		temp.getPrevious().setNext(insert);
		insert.setPrevious(temp.getPrevious());
		insert.setNext(temp);
		temp.setPrevious(insert);
		size++;
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
		ListNode temp = getHelper(index);
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
	}
	
	public void forEach(Processor processor) {	
		for(ListNode temp = first; temp!=null ;temp=temp.getNext()) {
			processor.process(temp.getValue());
		}
	}
	public Object[] toArray() {
		ListNode temp = first;
		Object[] retval = new Object[size];
		for(int i=0;i<size;i++) {
			retval[i]=temp.getValue();
			temp = temp.getNext();
		}
		return retval;
	}
	public boolean contains(Object value) {
		ListNode temp = first;
		for(int i=0;i<size;i++) {
			if(temp.getValue().equals(value)) return true;
			temp = temp.getNext();
		}
		return false;
		
	}
//	public boolean remove(Object value) {
//		if(value==null)throw new NullPointerException("Given object was null");
//		ListNode temp = first;
//		for(int i=0;i<size;i++) {
//			if(temp.getValue().equals(value)) {
//				remove(i);
//				return true;
//			};
//			temp = temp.getNext();
//		}
//		return false;
//	}
}
