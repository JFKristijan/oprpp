package hr.fer.oprpp1.hw08.jnotepadpp;

/**Interface that defines a listener used to listen to changes in a {@link SingleDocumentModel} object
 * @author Fran Kristijan Jelenčić
 *
 */
public interface SingleDocumentListener {
	
	/**Notification that the modification status of the model has changed
	 * @param model that has been modified in some way
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	
	/**Notification that the documents file path has been updated
	 * @param model that has had its file path changed
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
