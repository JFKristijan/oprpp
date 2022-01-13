package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;




/**
 * @author Fran Kristijan Jelenčić
 * Implementation of a simple hashtable
 * @param <K>
 * @param <V>
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	public static class TableEntry<K,V>{
		private K key;
		private V value;
		private TableEntry<K,V> next;
		
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
		public TableEntry<K, V> getNext() {
			return next;
		}
		public void setNext(TableEntry<K, V> next) {
			this.next = next;
		}
		public K getKey() {
			return key;
		} 
		public TableEntry(K key, V value) {
			this.key=key;
			this.value=value;
		}
		public String toString() {
			return key+"="+value;
		}
	
	}
	private TableEntry<K,V>[] table;
	private int size;
	private int modificationCount;
	public SimpleHashtable(){
		this(16);
	}
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int x) {
		if(x<1)throw new IllegalArgumentException("Size can not be less than 1");
		double num=Math.log(x)/Math.log(2);
		//2**(ceil of log2(x))
		table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class,(int) Math.pow(2, Math.ceil(num)));
		size=0;
	}
	
	/**Method that puts the given value at the given key, if the key already exists returns the old value of
	 * the given key and replaces it with the new one
	 * @param key Key of the given value
	 * @param value the value that is paired with the given key
	 * @return value of the new key-value pair or the old value of the key if it already exists
	 * @throws NullPointerException if the given key is null
	 */
	public V put(K key, V value) {
		if(key==null)throw new NullPointerException("Key can not be null");
		if((double)size/table.length>0.75)realloc();
		modificationCount++;
		int index = Math.abs(key.hashCode()%table.length);
		TableEntry<K,V> newElement =new TableEntry<K, V>(key,value);
		
		if(table[index]==null) {
			table[index]=newElement;
			size++;
			return value;
		}else {
			TableEntry<K,V> temp = table[index];
			while(temp.getNext()!=null&&!temp.getKey().equals(key))temp=temp.getNext();
			if(temp.getNext()==null) {
				temp.setNext(newElement);
				size++;
				return temp.getValue();
			}else {
				V retval = temp.getValue();
				temp.setValue(value);
				return retval;
			}
			
		}
	}
	
	
	/**Method that gets the value at given key
	 * @param key the key from which to get the value
	 * @return value paired with the given key
	 */
	public V get(Object key) {
		int index = Math.abs(key.hashCode()%table.length);
		if(table[index]==null)return null;
		
		TableEntry<K,V> temp = table[index];
		while(temp.getNext()!=null&&!temp.getKey().equals(key))temp=temp.getNext();
		if(temp.getKey().equals(key))return temp.getValue();
		
		return null;
	}
	
	
	/**Method that returns the amount of elements in the table
	 * @return int that represents the size of the table
	 */
	public int size() {
		return size;
	}
	
	/**Checks if the given key exists in the table
	 * @param key The key to check if it exists
	 * @return true if the key exists, false otherwise
	 */
	public boolean containsKey(Object key) {
		if(get(key)==null)return false;
		return true;
	}
		
	
	/**Checks if the given value exists in the table
	 * @param value the value to be checked if it exists in the table
	 * @return true if the value is in the table, false otherwise
	 */
	public boolean containsValue(Object value) {
		for(int i = 0 ; i < table.length ; i++) {
			if(table[i]!=null) {
				TableEntry<K,V> temp = table[i];
				while(temp.getNext()!=null) {
					if(temp.getValue().equals(value))return true;
					temp=temp.getNext();
				}
			}
		}
		return false;
	}
	
	/**Method that removes the given key value pair from this table
	 * @param key the key which to remove
	 * @return value of the given key if it exists, null otherwise
	 * @throws NullPointerException if the given kez is <code>null</code>
	 */
	public V remove(Object key) {
		if(key==null)throw new NullPointerException("Key can not be null");
		int index = Math.abs(key.hashCode()%table.length);
		if(table[index]==null)return null;
		modificationCount++;
		size--;
		TableEntry<K,V> temp = table[index];
		if(temp.getKey().equals(key)) {
			if(temp.getNext()!=null) {
				table[index]=temp.getNext();
			}else {
				table[index]=null;
			}
			return temp.getValue();
		}
		TableEntry<K,V> tempPrev=table[index];
		while(!temp.getKey().equals(key)&&temp.getNext()!=null) {
			tempPrev=temp;
			temp=temp.getNext();
		}
		tempPrev.setNext(temp.getNext());
		return temp.getValue();
		
	}
	
	
	/**Method that check whether this table is empty
	 * @return true if the table is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size==0;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(TableEntry<K,V> te:toArray()) {
			sb.append(te.toString()+", ");
		}
//		for(int i = 0 ; i < table.length ; i++) {
//			if(table[i]!=null) {
//				TableEntry<K,V> temp = table[i];
//				sb.append(temp.toString()+", ");
//				while(temp.getNext()!=null) {
//					temp=temp.getNext();
//					if(temp!=null)sb.append(temp.toString()+", ");
//				}
//			}
//		}
		if(sb.length()>2)sb.setLength(sb.length()-2);
		sb.append("]");
		return sb.toString();
	}
	
	
	/**Method that returns all the elements of the table in an array
	 * @return TableEntry<K,V> array that contains all the elements of this table
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray(){
		TableEntry<K,V>[] retval = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, size);
		int index = 0;
		for(int i = 0 ; i < table.length ; i++) {
			if(table[i]!=null) {
				TableEntry<K,V> temp = table[i];
				retval[index++]=temp;
				while(temp.getNext()!=null) {
					temp=temp.getNext();
					if(temp!=null)retval[index++]=temp;
				}
			}
		}
		return retval;
	}
	@SuppressWarnings("unchecked")
	private void realloc() {
		TableEntry<K,V>[] currentTable = toArray();
		table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class,table.length*2);
		size=0;
		for(int i = 0 ; i< currentTable.length ; i++) put(currentTable[i].key,currentTable[i].value);
	}
	
	
	/**Clears the collection of all entries
	 * 
	 */
	public void clear() {
		for(int i = 0 ; i< table.length ; i++) 
			table[i]=null;
		size=0;
	}
	
	/**Class that represents an iterator implementation for {@link SimpleHashtable}
	 * @author Fran Kristijan Jelenčić
	 *
	 */
	private class IteratorImplem implements Iterator<TableEntry<K, V>>{
		private int curr;
		int lastIndex;
		private long savedModificationcount;
		private boolean remove;
		private TableEntry<K,V> temporary;
		public IteratorImplem() {
			savedModificationcount=modificationCount;
		}
		
		/**Returns true if the table has more elements
		 *
		 */
		@Override
		public boolean hasNext() {
			if(savedModificationcount!=modificationCount)throw new ConcurrentModificationException();
			return curr<size;
		}

		
		/**
		 *Returns the next element of the table
		 *@return the next element of the table
		 *@throws ConcurrentModificationException if the table has been modified in between calls of iterator methods
		 */
		@Override
		public TableEntry<K, V> next() {
			if(savedModificationcount!=modificationCount)throw new ConcurrentModificationException();
			remove=false;
			if(temporary!=null&&temporary.getNext()!=null) {
				TableEntry<K,V> retval = temporary.getNext();
				temporary=temporary.getNext();
				curr++;
				return retval;
			}else {
				for(; lastIndex < table.length && table[lastIndex]==null; lastIndex++);
				try {
					temporary=table[lastIndex++];
				}catch(IndexOutOfBoundsException e) {
					throw new NoSuchElementException();
				}
				curr++;
				return temporary;
			}
		}
		
		
		/**Removes the last element that the iterator returned from the collection, can only be called once
		 *@throws ConcurrentModificationException if the table has been modified in between calls of iterator methods
		 *@throws IllegalStateException if the remove method is called twice in a row
		 */
		public void remove() {
			if(savedModificationcount!=modificationCount)throw new ConcurrentModificationException();
			if(remove)throw new IllegalStateException();
			if(temporary==null)throw new IllegalStateException("No element to remove");
			SimpleHashtable.this.remove(temporary.key);
			remove=true;
			curr--;
			savedModificationcount++;	
		}
		
	}
	
	/**
	 *Returns an iterator implementation specific to this table
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImplem();
	}
}
