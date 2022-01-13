package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**Abstract class used for providing localization support
 * @author Fran Kristijan Jelenčić
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	List<ILocalizationListener> listeners;
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	

	public void addLocalizationListener(ILocalizationListener l) {listeners.add(l);}
	
	public void removeLocalizationListener(ILocalizationListener l) {listeners.remove(l);}
	
	/**Notifies all the subscribed listeners that localization has changed
	 * 
	 */
	public void fire() {listeners.forEach(l->l.localizationChanged());}
}
