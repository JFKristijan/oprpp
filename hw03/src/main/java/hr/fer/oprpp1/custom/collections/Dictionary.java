package hr.fer.oprpp1.custom.collections;

/**Class that represents a dictionary
 * @author Fran Kristijan Jelenčić
 *
 * @param <K> type of Key
 * @param <V> type of Value
 */
public class Dictionary<K,V> {
	private static class Pair<K,V>{
		private K key;
		private V value;
		public Pair(K key, V value) {
			if(key==null)throw new NullPointerException("Key can not be null!");
			this.key=key;
			this.value=value;
		}

	}
	private ArrayIndexedCollection<Pair<K,V>> elements;
	public Dictionary() {
		elements = new ArrayIndexedCollection<Pair<K,V>>();
	}
	
	/**Method that returns whether or not the dictionary is empty
	 * @return true if the dictionary is empty, false otherwise
	 */
	public boolean isEmpty() {
		return elements.size()==0;
	}
	
	/**Method that returns the size of this dictionary
	 * @return int that is the current size of this dictionary
	 */
	public int size() { return elements.size();}
	
	
	/**Method that removes all entries in this dictionary
	 * 
	 */
	public void clear() { elements.clear();}
	
	
	/**Method that adds the given key, value pair into the dictionary, if the key already exists overwrites the old value with the new one and returns the old value
	 * @param key the key of the value	
	 * @param value the value that is to be assigned to the key
	 * @return value that is put if no entries with same key exist, old value of the given key otherwise
	 */
	public V put(K key,V value) {
		V retval = value;
		Pair<K,V> temp = getHelper(key);
		if(temp!=null) {
			retval=temp.value;
			temp.value=value;
		}else {
			elements.add(new Pair<K, V>(key, value));
		}
		return retval;
		}
	
	
	/**Method that gets the value at given key
	 * @param key the key to get the value from
	 * @return Value paired with the  key, null if no value exists at given key
	 */
	public V get(Object key) {
		Pair<K,V> temp = getHelper(key);
		if(temp!=null)return temp.value;
		return null;
	}
	
	/**Private function that gets the Pair<K,V> at given key
	 * @param key the key to get the value from
	 * @return Pair<K,V> paired with the  key, null if no value exists at given key
	 */
	private Pair<K,V> getHelper(Object key){
		ElementsGetter<Pair<K,V>> eg = elements.createElementsGetter();
		while(eg.hasNextElement()) {
			Pair<K,V> temp = eg.getNextElement();
			if(temp.key.equals(key))return temp;
		}
		return null;
	}
	
	
	/**Method that removes value at given key
	 * @param key remove value at given key
	 * @return value of the key that has been removed, null if it does not exist
	 */
	public V remove(K key) {
		Pair<K,V> temp = getHelper(key);
		V retval = null;
		if(temp!=null) {
			retval = temp.value;
			elements.remove(temp);
		}
		return retval;
	}
}


