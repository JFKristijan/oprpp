package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

/**Interface used to describe a model that holds multiple {@link SingleDocumentModel}s
 * @author Fran Kristijan Jelenčić
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**Creates a new empty {@link SingleDocumentModel} and add it to the list of models
	 * @return the new {@link SingleDocumentModel}
	 */
	SingleDocumentModel createNewDocument();
	
	
	/**Returns the currently active document
	 * @return the current {@link SingleDocumentModel}
	 */
	SingleDocumentModel getCurrentDocument();
	
	
	/**Loads a document with the given path, and adds it to the list of models
	 * @param path to load the document from
	 * @return the loaded {@link SingleDocumentModel}
	 */
	SingleDocumentModel loadDocument(Path path);
	
	
	/**Saves the given {@link SingleDocumentModel} to the given Path
	 * @param model to save
	 * @param newPath to save it on
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	
	/**Removes the document from the list of models
	 * @param model to remove from the list of models
	 */
	void closeDocument(SingleDocumentModel model);
	
	
	/**Adds the given listener to the list of listeners
	 * @param l listener to add
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**Removes the given listener to the list of listeners
	 * @param l listener to remove from the list
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**Returns the number of documents in this {@link MultipleDocumentModel}
	 * @return number of documents
	 */
	int getNumberOfDocuments();
	
	/**Returns the document at given index
	 * @param index to get the model from
	 * @return Model at the given index
	 */
	SingleDocumentModel getDocument(int index);
}
