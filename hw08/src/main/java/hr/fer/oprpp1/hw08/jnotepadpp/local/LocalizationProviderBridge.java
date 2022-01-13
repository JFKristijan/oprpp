package hr.fer.oprpp1.hw08.jnotepadpp.local;



/**Class implemented as a decorator for some other {@link ILocalizationProvider}
 * @author Fran Kristijan Jelenčić
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	private ILocalizationProvider parent;
	private boolean connected = false;
	private ILocalizationListener listener;
	
	
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		parent=provider;
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				LocalizationProviderBridge.this.fire();
			}
		};
	}
	
	/**Connects a listener to the parent ILocalizationProvider, does nothing if already connected
	 * 
	 */
	public void connect() {
		if(connected)
			return;
		parent.addLocalizationListener(listener);
		connected = true;
	}
	
	/**Disconnects a listener to the parent ILocalizationProvider, does nothing if already disconnected
	 * 
	 */
	public void disconnect() {
		if(!connected)
			return;
		parent.removeLocalizationListener(listener);
		connected = false;
	}
	
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
