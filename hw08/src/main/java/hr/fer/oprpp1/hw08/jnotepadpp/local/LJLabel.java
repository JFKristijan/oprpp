package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;

/**Class used as a localizable Label, extends {@link JLabel}
 * @author Fran Kristijan Jelenčić
 *
 */
public class LJLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	private String key;
	private ILocalizationProvider lp;
	private ILocalizationListener listener;
	
	/**Constructor for LJLabel, accepts key for localization and a {@link LocalizationProvider}
	 * @param key to get from resource bundle
	 * @param lp provider that provides localized strings
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		this.key=key;
		this.lp=lp;
		this.listener = this::change;
		change();
		lp.addLocalizationListener(listener);
	}

	private void change() {
		
		setText(lp.getString(key));

	}
	
	
}
