package hr.fer.oprpp1.hw08.jnotepadpp;

/**Interface used to describe a listener for {@link MultipleDocumentModel}
 * @author Fran Kristijan Jelenčić
 *
 */
public interface MultipleDocumentListener {
	
	/**Notification that the current document has changed
	 * @param previousModel the model that was current before the change
	 * @param currentModel 
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel,SingleDocumentModel currentModel);
	
	/**Notification that a document has been added
	 * @param model that has been added
	 */
	void documentAdded(SingleDocumentModel model);
	
	
	/**Notification that a document has been removed
	 * @param model that has been removed
	 */
	void documentRemoved(SingleDocumentModel model);
}
