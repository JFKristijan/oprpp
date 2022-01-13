package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{


	private static final long serialVersionUID = 1L;
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel currentDocument;
	private List<MultipleDocumentListener> listeners;
	private static ImageIcon saved;
	private static ImageIcon unsaved;

	public DefaultMultipleDocumentModel() {
		saved = getIcon("saved.png");
		unsaved = getIcon("unsaved.png");
		documents = new ArrayList<SingleDocumentModel>();
		listeners = new ArrayList<MultipleDocumentListener>();
		addChangeListener(l->{
			SingleDocumentModel old = getCurrentDocument();
			currentDocument = documents.get(getSelectedIndex());
			notifyListeners(e->e.currentDocumentChanged(old, currentDocument));
		});
	}


	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return addDocument(null,"");
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {

		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		int index = 0;
		for(SingleDocumentModel model : documents) {
			if(model.getFilePath() != null && model.getFilePath().equals(path)) {
				notifyListeners(l->l.currentDocumentChanged(currentDocument, model));
				currentDocument = model;
				setSelectedIndex(index);
				return model;
			}
		}
		
		byte[] read;
		
		try {
			read = Files.readAllBytes(path);
		} catch (IOException e) {
			throw new RuntimeException("Error reading file");
		}
		
		
		return addDocument(path, new String(read, StandardCharsets.UTF_8));

	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		
		for(SingleDocumentModel document:documents) {
			
			if(document.equals(model)) {
				continue;
			}
			
			if(document.getFilePath() != null && document.getFilePath().equals(newPath)) {
				throw new RuntimeException("Specified file is already opened");
			}
			
		}

		byte[] toSave = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		
		if(newPath!=null) {
			model.setFilePath(newPath);
		
		}
		
		Path savePath = model.getFilePath();
		
		try {
			
			Files.write(savePath, toSave);
			
		} catch (IOException e) {
			
			throw new RuntimeException("Unable to save file!");
			
		}
		
		currentDocument.setModified(false);


	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		
		int index = documents.indexOf(model)-1;
		
		if(documents.size()==1) {
			createNewDocument();
		}
		
		if(index<0) {
			index=0;
		}
		
		removeTabAt(documents.indexOf(model));
		setSelectedIndex(index);
		
		documents.remove(model);
		currentDocument=documents.get(index);
		
		
		notifyListeners(l->l.documentRemoved(model));
		notifyListeners(l->l.currentDocumentChanged(model, currentDocument));

	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);	
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	private ImageIcon getIcon(String name) {
		InputStream is = JNotepadPP.class.getResourceAsStream("icons/"+name);
		if(is==null) {
			throw new RuntimeException("Could not load icon "+name);
		}
		byte[] read;
		try {
			read = is.readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException("Error reading icon data"+name);
		}
		return new ImageIcon(read);
	}
	
	
	
	private SingleDocumentModel addDocument(Path path,String text) {
		SingleDocumentModel retDocument = new DefaultSingleDocumentModel(path, text);
		retDocument.addSingleDocumentListener(new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(documents.indexOf(model), (model.isModified()?unsaved:saved));
				
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				updatePath(model);
				
			}
		});
		
		documents.add(retDocument);
		notifyListeners(l->l.documentAdded(retDocument));
		
		notifyListeners(l->l.currentDocumentChanged(currentDocument, retDocument));
		currentDocument = retDocument;
		
		addPane(retDocument);
		
		return retDocument;
	}
	
	private void addPane(SingleDocumentModel model) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(model.getTextComponent()),BorderLayout.CENTER);
		String name = model.getFilePath()==null?"Unnamed":model.getFilePath().getFileName().toString();
		String path = model.getFilePath()==null?"":model.getFilePath().toAbsolutePath().toString();
		addTab(name, getIcon("saved.png"), panel, path);
		setSelectedComponent(panel);
	}
	
	private void updatePath(SingleDocumentModel model) {
		setTitleAt(documents.indexOf(model), model.getFilePath().getFileName().toString());
		setToolTipTextAt(documents.indexOf(model), model.getFilePath().toString());
		
	}
	
	
	private void notifyListeners(Consumer<MultipleDocumentListener> notif) {
		listeners.forEach(notif::accept);
	}
	
}
