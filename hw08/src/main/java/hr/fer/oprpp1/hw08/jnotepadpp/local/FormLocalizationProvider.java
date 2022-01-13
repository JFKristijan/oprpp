package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**Class used to connect a {@link JFrame} with a {@link ILocalizationProvider} to allow localization
 * @author Fran Kristijan Jelenčić
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**Constructor for {@link FormLocalizationProvider}, used to attach a listener to {@link ILocalizationProvider} for localization,
	 * @param provider Localization provider
	 * @param frame to attach the provider to
	 */
	public FormLocalizationProvider(ILocalizationProvider provider,JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
		});
	}

}
