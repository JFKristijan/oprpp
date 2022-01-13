package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private JTextArea textComponent;
	private JScrollPane pane;
	private Path filePath;
	private boolean modify;
	private List<SingleDocumentListener> listeners;
	
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		listeners = new ArrayList<SingleDocumentListener>();
		textComponent = new JTextArea(textContent);
		this.filePath = filePath;
		modify = false;
		this.textComponent.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				modify=true;
				notifyListeners();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				modify=true;
				notifyListeners();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				modify=true;
				notifyListeners();
			}
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {

		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		filePath = path;
		listeners.forEach(l->l.documentFilePathUpdated(DefaultSingleDocumentModel.this));
	}

	@Override
	public boolean isModified() {

		return modify;
	}

	@Override
	public void setModified(boolean modified) {

		modify=modified;
		notifyListeners();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);

	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);

	}
	private void notifyListeners() {
		listeners.forEach(l->l.documentModifyStatusUpdated(this));
	}

}
