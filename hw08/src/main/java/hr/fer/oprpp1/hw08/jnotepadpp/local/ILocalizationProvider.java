package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**Interface for a localization provider
 * @author Fran Kristijan Jelenčić
 *
 */
public interface ILocalizationProvider {
	
	/**Method returns the localized string for given key
	 * @param key of 
	 * @return
	 */
	String getString(String key);
	
	/**Removes the listener from the list of listeners
	 *@param ILocalizationListener l the listener to remove
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**Adds an {@link ILocalizationListener} to the providers list of listeners 
	 *@param ILocalizationListener l listener to add
	 */
	void addLocalizationListener(ILocalizationListener l);
}
