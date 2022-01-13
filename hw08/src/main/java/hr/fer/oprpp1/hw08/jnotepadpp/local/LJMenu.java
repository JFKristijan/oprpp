package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JMenu;

/**Class used to enable localization of Menu bars
 * @author Fran Kristijan Jelenčić
 *
 */
public class LJMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private String key;
	private ILocalizationProvider lp;
	private ILocalizationListener listener;
	
	/**Constructor for {@link LJMenu} used to enable localization
	 * @param key for menu bar name
	 * @param lp localization provider that supplies the localized strings of the given key
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
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
