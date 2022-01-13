package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**Interface that defines a single document used in {@link JNotepadPP}
 * @author Fran Kristijan Jelenčić
 *
 */
public interface SingleDocumentModel {
	
	
	/**Getter for text component of the model
	 * @return
	 */
	JTextArea getTextComponent();
	
	
	/**Returns file path, if file has not been saved returns null
	 * @return file path of the document
	 */
	Path getFilePath();
	
	
	/**Sets the file path and notifies listeners that the path has changed
	 * @param path that will be the new path of the document
	 */
	void setFilePath(Path path);
	
	/**Returns whether the document has been modified
	 * @return true if the document has been modified, false otherwise
	 */
	boolean isModified();
	
	
	/**Sets the modified value of the document and notifies its listeners of the change
	 * @param modified new value of modified atribute
	 */
	void setModified(boolean modified);
	
	/**Adds the given listener to the list of listeners of the model
	 * @param l listener to add
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	
	/**removes the given listener from the list of listeners of the model, relies on equals method
	 * @param l listener to remove
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
