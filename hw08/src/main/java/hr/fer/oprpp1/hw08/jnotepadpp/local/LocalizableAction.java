package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

/**Class used as a localizable action, name and short description change with language changes
 * @author Fran Kristijan Jelenčić
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private String key;
	private ILocalizationProvider lp;
	private ILocalizationListener listener;

	/**Constructor for {@link LocalizableAction}
	 * @param key that holds the name and short description of the action
	 * @param lp provider that provides localized strings
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key=key;
		this.lp=lp;
		this.listener = this::change;
		change();
		lp.addLocalizationListener(listener);
	}

	private void change() {
		
		
		putValue(NAME,lp.getString(key));
		putValue(SHORT_DESCRIPTION,lp.getString(key+"_desc"));
	}




}
