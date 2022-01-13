package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**Singleton class used to provide localization from translations located in locale package in resources
 * @author Fran Kristijan Jelenčić
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	private String language;
	
	private ResourceBundle bundle;
	
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	private  LocalizationProvider() {
		setLanguage("en");
	}
	/**Method that returns an instance of {@link LocalizationProvider}
	 * @return
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
		
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	/**Sets the current language to the language given
	 * @param language to set as current language
	 */
	public void setLanguage(String language) {
		if(this.language!=null&&this.language.equals(language))
			return;
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.locale.prijevodi", locale);
		fire();
	}
	
	/**Getter for current language
	 * @return String that is the name of the language
	 */
	public String getLanguage() {
		return language;
	}
}
