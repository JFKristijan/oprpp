package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**List Model used for creating a list of prime numbers, initally has one element "1"
 * @author Fran Kristijan Jelenčić
 *
 */
public class PrimListModel implements ListModel<Integer>  {
	
	public PrimListModel() {
		primList.add(1);
	}
	
	private ArrayList<Integer> primList = new ArrayList<Integer>();
	private LinkedList<ListDataListener> listeners = new LinkedList<ListDataListener>();
	
	
	/**Function that checks if number is prime
	 * @param n
	 * @return
	 */
	private boolean isPrim(int n) {
		for (int i=2; i<=Math.sqrt(n); i++) {
			if(n%i == 0)
				return false;
		}
		return true;
	}
	
	/**Getter for size variable
	 *
	 */
	@Override
	public int getSize() {
		return primList.size();
	}

	/**Gets the element at given index
	 *
	 */
	@Override
	public Integer getElementAt(int index) {
		return primList.get(index);
	}

	/**Adds a listener to the list that's notified each time a change to the data model occurs.
	 *
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	/**Removes a listener from the list that's notified each time achange to the data model occurs.
	 *
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
	/**Method that searches for and adds another prime number to the list
	 * 
	 */
	public void next() {
		
		int search = primList.get(getSize()-1)+1;

		while(!isPrim(search)) 
			search++;
		primList.add(search);
		
		listeners.forEach(e->e.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, primList.size()-1, primList.size()-1)));
		
		
	}

}
