package hr.fer.oprpp1.hw08.jnotepadpp;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JToolBar.Separator;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;



import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJLabel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;



/**Class that is used to run the {@link JNotepadPP} app
 * @author Fran Kristijan Jelenčić
 *
 */
public class JNotepadPP extends JFrame{

	private static final long serialVersionUID = 1L;
	private DefaultMultipleDocumentModel model;
	//private Path openedFilePath;
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	private JMenu toolsMenu;


	/**Constructor that initialises all values, actions and menus
	 * 
	 */
	public JNotepadPP() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);

		initGUI();

		WindowListener wl = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		};
		addWindowListener(wl);

	}

	/**Private method that initializes the Graphical User Interface
	 * 
	 */
	private void initGUI() {

		initModel();
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(
				model, BorderLayout.CENTER
				);	


		createActions();
		createMenus();
		createToolbars();
		flp.addLocalizationListener(()->setStats());

	}

	/**Private method that initalizes the model used to edit text
	 * 
	 */
	private void initModel() {
		model = new DefaultMultipleDocumentModel(); 
		model.createNewDocument();
		model.getCurrentDocument().getTextComponent().getCaret().addChangeListener(caretListener);
		setTitle("(unnamed)  -  JNotepad++");
		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				String name = currentModel.getFilePath()==null?"(unnamed) ":currentModel.getFilePath().toString();
				setTitle(name+" -  JNotepad++");
				if(previousModel!=null)
					previousModel.getTextComponent().getCaret().removeChangeListener(caretListener);
				currentModel.getTextComponent().getCaret().addChangeListener(caretListener);

				checkCaret(currentModel.getTextComponent().getCaret());

				setStats();
			}
		});



	}

	/**Private method used to close the app, checks if there are unsaved documents and shows a message dialogue asking what to do with the unsaved documents
	 * 
	 */
	private void close() {

		model.setSelectedIndex(0);
		int numDocs = model.getNumberOfDocuments();
		for(int i = 0; i<numDocs ;i++) {
			if(model.getCurrentDocument().isModified()) {
				boolean t = closeSaving();
				if(!t) {
					return;
				}

			}else {
				model.closeDocument(model.getCurrentDocument());
			}
		}
		timer.stop();
		dispose();

	}

	/**Private method that is used to enable or disable the Tools menu based on whether there is text selected or not
	 * @param c 
	 */
	private void checkCaret(Caret c) {
		int selectedSize = Math.abs(c.getDot()-c.getMark());
		toolsMenu.setEnabled(selectedSize!=0);
		cutAction.setEnabled(selectedSize!=0);
		copyAction.setEnabled(selectedSize!=0);
	}

	private ChangeListener caretListener = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			setStats();
			checkCaret((Caret)e.getSource());


		}
	};

	/**Method used to close a document while showing a document asking whether to save it or discard changes
	 * @return booelan true if file is closed, false if operation is cancelled
	 */
	private boolean closeSaving() {
		String[] options = new String[] {flp.getString("yes"),flp.getString("no"),flp.getString("cancel")};
		int result = JOptionPane.showOptionDialog(JNotepadPP.this,
				flp.getString("unsaved"),
				flp.getString("saveFile"),
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[0]);
		switch(result) {
		case JOptionPane.CANCEL_OPTION:
			return false;
		case JOptionPane.YES_OPTION:
			if(!save(model.getCurrentDocument().getFilePath(),false))
				return false;
			model.closeDocument(model.getCurrentDocument());
			return true;
		case JOptionPane.NO_OPTION:
			model.closeDocument(model.getCurrentDocument());
			return true;
		default:
			return false;
		}

	}

	/**Private method used to save files, if no path is supplied opens a {@link JFileChooser}, if user is saving as and the file at the path exists asks user whether to overwrite it
	 * @param openedFilePath path to save to
	 * @param saveAs boolean if user has triggered the save as option or just saving
	 */
	private boolean save(Path openedFilePath,boolean saveAs) {
		boolean noPath = false;
		if(openedFilePath==null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle(flp.getString("saveFile"));
			if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						flp.getString("nothingSaved"), 
						flp.getString("warning"), 
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
			noPath = true;
			openedFilePath = jfc.getSelectedFile().toPath();
			//this.openedFilePath=openedFilePath;
		}
		if(saveAs||noPath) {
			if(Files.exists(openedFilePath)) {
				String[] options = new String[] {flp.getString("yes"),flp.getString("no")};
				int result = JOptionPane.showOptionDialog(JNotepadPP.this,
						flp.getString("fileExists"),
						flp.getString("saveFile"),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE,
						null,
						options,
						options[1]);
				if(result!=JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							flp.getString("nothingSaved"), 
							flp.getString("warning"), 
							JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}

		try {
			model.saveDocument(model.getCurrentDocument(), openedFilePath);
		} catch (RuntimeException e1) {
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					String.format(flp.getString("errorSaving"),openedFilePath.toFile().getAbsolutePath()), 
					flp.getString("error"), 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		setTitle(openedFilePath+" -  JNotepad++");
		setStats();
		JOptionPane.showMessageDialog(
				JNotepadPP.this, 
				flp.getString("saved"), 
				flp.getString("information"), 
				JOptionPane.INFORMATION_MESSAGE);
		return true;

	}

	/**Sorts the given text based on current locale
	 * @param text to sort
	 * @param ascending whether to sort the text ascending or descending
	 * @return String the lines sorted in ascending/descending order
	 */
	private String sort(String text,boolean ascending) {

		Locale currentLocale = new Locale(LocalizationProvider.getInstance().getLanguage());
		Collator collator = Collator.getInstance(currentLocale);
		List<String> lines = new ArrayList<>(Arrays.asList(text.split("\n")));
		lines.sort(ascending?collator:collator.reversed());
		StringBuilder sb = new StringBuilder();
		lines.forEach(s->sb.append(s+"\n"));
		sb.setLength(sb.length()-1);
		return sb.toString();
	}


	private Action statisticalAction = new LocalizableAction("stats",flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String text =  model.getCurrentDocument().getTextComponent().getText();
			long length = text.length();
			long lengthChar = text.replaceAll("\s+", "").length();
			int noLines = model.getCurrentDocument().getTextComponent().getLineCount();
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					String.format(flp.getString("docStats"),length,lengthChar,noLines), 
					flp.getString("information"), 
					JOptionPane.INFORMATION_MESSAGE);
			
		}
	};

	private Action newDocumentAction = new LocalizableAction("new",flp) {


		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}

	};




	private Action openDocumentAction = new LocalizableAction("open",flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						String.format(flp.getString("fileNotExist"), fileName.getAbsolutePath()), 
						flp.getString("error"), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			model.loadDocument(filePath);
			//openedFilePath = filePath;
		}
	};


	private Action saveAsDocumentAction = new LocalizableAction("saveAs",flp) {


		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			save(null,true);

		}

	};
	private Action saveDocumentAction = new LocalizableAction("save",flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			save(model.getCurrentDocument().getFilePath(),false);
		}
	};

	private Action closeDocumentAction = new LocalizableAction("close",flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!model.getCurrentDocument().isModified()) {
				model.closeDocument(model.getCurrentDocument());
				return;
			}
			closeSaving();

		}
	};



	private Action copySelectedPartAction = new LocalizableAction("copy",flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = model.getCurrentDocument().getTextComponent();
			editor.copy();
		}};

		private Action cutSelectedPartAction = new LocalizableAction("cut",flp) {


			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				editor.cut();
				
			}};

			private Action pasteAction = new LocalizableAction("paste",flp) {


				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextArea editor = model.getCurrentDocument().getTextComponent();
					editor.paste();
				}};

				private Action deleteSelectedPartAction = new LocalizableAction("delete",flp) {


					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						Document doc = model.getCurrentDocument().getTextComponent().getDocument();
						JTextArea editor = model.getCurrentDocument().getTextComponent();
						int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
						if(len==0) return;
						int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
						try {
							doc.remove(offset, len);
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
					}
				};


				private Action toggleCaseAction = new LocalizableAction("toggleCase",flp) {



					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						Document doc = model.getCurrentDocument().getTextComponent().getDocument();
						JTextArea editor = model.getCurrentDocument().getTextComponent();
						int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
						int offset = 0;
						if(len!=0) {
							offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
						} else {
							return;
						}
						try {
							String text = doc.getText(offset, len);
							text = changeCase(text);
							doc.remove(offset, len);
							doc.insertString(offset, text, null);
						} catch(BadLocationException ex) {
							ex.printStackTrace();
						}
					}

					private String changeCase(String text) {
						char[] znakovi = text.toCharArray();
						for(int i = 0; i < znakovi.length; i++) {
							char c = znakovi[i];
							if(Character.isLowerCase(c)) {
								znakovi[i] = Character.toUpperCase(c);
							} else if(Character.isUpperCase(c)) {
								znakovi[i] = Character.toLowerCase(c);
							}
						}
						return new String(znakovi);
					}
				};

				private Action upperCaseAction =  new LocalizableAction("upperCase",flp) {



					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						Document doc = model.getCurrentDocument().getTextComponent().getDocument();
						JTextArea editor = model.getCurrentDocument().getTextComponent();
						int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
						int offset = 0;
						if(len!=0) {
							offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
						} else {
							return;
						}
						try {
							String text = doc.getText(offset, len);
							text = changeCase(text);
							doc.remove(offset, len);
							doc.insertString(offset, text, null);
						} catch(BadLocationException ex) {
							ex.printStackTrace();
						}
					}

					private String changeCase(String text) {
						char[] znakovi = text.toCharArray();
						for(int i = 0; i < znakovi.length; i++) {
							char c = znakovi[i];
							znakovi[i] = Character.toUpperCase(c);

						}
						return new String(znakovi);
					}
				};

				private Action lowerCaseAction =  new LocalizableAction("lowerCase",flp) {



					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						Document doc = model.getCurrentDocument().getTextComponent().getDocument();
						JTextArea editor = model.getCurrentDocument().getTextComponent();
						int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
						int offset = 0;
						if(len!=0) {
							offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
						} else {
							return;
						}
						try {
							String text = doc.getText(offset, len);
							text = changeCase(text);
							doc.remove(offset, len);
							doc.insertString(offset, text, null);
						} catch(BadLocationException ex) {
							ex.printStackTrace();
						}
					}

					private String changeCase(String text) {
						char[] znakovi = text.toCharArray();
						for(int i = 0; i < znakovi.length; i++) {
							char c = znakovi[i];
							znakovi[i] = Character.toLowerCase(c);

						}
						return new String(znakovi);
					}
				};

				private Action ascendingSortAction = new LocalizableAction("ascending",flp) {


					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						Document doc = model.getCurrentDocument().getTextComponent().getDocument();
						JTextArea editor = model.getCurrentDocument().getTextComponent();
						int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
						int offset = 0;

						try {
							if(len!=0) {
								offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
								offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));

								len = Math.max(editor.getCaret().getDot(),editor.getCaret().getMark());
								len = editor.getLineEndOffset(editor.getLineOfOffset(len))-offset;
							} else {
								return;
							}
							String text = doc.getText(offset, len);
							text = sort(text,true);
							doc.remove(offset, len);
							doc.insertString(offset, text, null);
						} catch(BadLocationException ex) {
							ex.printStackTrace();
						}
					}
				};

				private Action descendingSortAction = new LocalizableAction("descending",flp) {


					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						Document doc = model.getCurrentDocument().getTextComponent().getDocument();
						JTextArea editor = model.getCurrentDocument().getTextComponent();
						int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
						int offset = 0;

						try {
							if(len!=0) {
								offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
								offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));

								len = Math.max(editor.getCaret().getDot(),editor.getCaret().getMark());
								len = editor.getLineEndOffset(editor.getLineOfOffset(len))-offset;
							} else {
								return;
							}
							String text = doc.getText(offset, len);
							text = sort(text,false);
							doc.remove(offset, len);
							doc.insertString(offset, text, null);
						} catch(BadLocationException ex) {
							ex.printStackTrace();
						}
					}
				};

				private Action uniqueAction = new LocalizableAction("unique",flp) {


					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						Document doc = model.getCurrentDocument().getTextComponent().getDocument();
						JTextArea editor = model.getCurrentDocument().getTextComponent();
						int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
						int offset = 0;

						try {
							if(len!=0) {
								offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
								offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));

								len = Math.max(editor.getCaret().getDot(),editor.getCaret().getMark());
								len = editor.getLineEndOffset(editor.getLineOfOffset(len))-offset;
							} else {
								return;
							}
							String text = doc.getText(offset, len);
							List<String> lines = new ArrayList<>(Arrays.asList(text.split("\n")));
							Set<String> uniqueLines = new LinkedHashSet<String>(lines);
							StringBuilder sb = new StringBuilder();
							uniqueLines.forEach(s->sb.append(s+"\n"));
							sb.setLength(sb.length()-1);
							doc.remove(offset, len);
							doc.insertString(offset, sb.toString(), null);
						} catch(BadLocationException ex) {
							ex.printStackTrace();
						}

					}
				};




				private Action exitAction = new LocalizableAction("exit",flp) {

					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						close();
					}
				};


				private Action engLangAction = new LocalizableAction("eng",flp) {


					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						LocalizationProvider.getInstance().setLanguage("en");

					}
				};

				private Action hrvLangAction = new LocalizableAction("hrv",flp) {


					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						LocalizationProvider.getInstance().setLanguage("hr");

					}
				};




				/**Method that maps actions and the mnemonic keys and hotkeys
				 * 
				 */
				private void createActions() {

					newDocumentAction.putValue(
							Action.ACCELERATOR_KEY,
							KeyStroke.getKeyStroke("control N"));
					newDocumentAction.putValue(
							Action.MNEMONIC_KEY,
							KeyEvent.VK_N);



					openDocumentAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("control O")); 
					openDocumentAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_O); 


					saveDocumentAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("control S")); 
					saveDocumentAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_S); 

					copySelectedPartAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("control C")); 
					copySelectedPartAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_C);

					cutSelectedPartAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("control X")); 
					cutSelectedPartAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_X);

					pasteAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("control V")); 
					pasteAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_V);

					deleteSelectedPartAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("F2")); 
					deleteSelectedPartAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_D); 



					toggleCaseAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("control F3")); 
					toggleCaseAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_T); 

					upperCaseAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("control F4")); 
					upperCaseAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_U); 

					lowerCaseAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("control F5")); 
					lowerCaseAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_L); 


					closeDocumentAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("control T"));
					closeDocumentAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_T); 

					exitAction.putValue(
							Action.ACCELERATOR_KEY, 
							KeyStroke.getKeyStroke("control Q"));
					exitAction.putValue(
							Action.MNEMONIC_KEY, 
							KeyEvent.VK_Q); 





				}

				private JButton copyAction = new JButton(copySelectedPartAction);
				private JButton cutAction = new JButton(cutSelectedPartAction);
				/**Creates and fills menus with actions
				 * 
				 */
				private void createMenus() {
					JMenuBar menuBar = new JMenuBar();

					JMenu fileMenu = new LJMenu("file",flp);
					menuBar.add(fileMenu);
					fileMenu.add(new JMenuItem(newDocumentAction));
					fileMenu.add(new JMenuItem(openDocumentAction));
					fileMenu.add(new JMenuItem(saveDocumentAction));
					fileMenu.add(new JMenuItem(saveAsDocumentAction));
					fileMenu.add(new JMenuItem(closeDocumentAction));
					fileMenu.addSeparator();
					fileMenu.add(new JMenuItem(exitAction));


					JMenu editMenu = new LJMenu("edit",flp);
					menuBar.add(editMenu);
					editMenu.add(new JMenuItem(copySelectedPartAction));
					editMenu.add(new JMenuItem(cutSelectedPartAction));
					editMenu.add(new JMenuItem(pasteAction));
					editMenu.add(new JMenuItem(deleteSelectedPartAction));



					JMenu langMenu = new LJMenu("languages",flp);
					langMenu.add(new JMenuItem(engLangAction));
					langMenu.add(new JMenuItem(hrvLangAction));

					menuBar.add(langMenu);


					toolsMenu = new LJMenu("tools",flp);

					JMenu changeCaseMenu = new LJMenu("changeCase",flp);
					changeCaseMenu.add(toggleCaseAction);
					changeCaseMenu.add(upperCaseAction);
					changeCaseMenu.add(lowerCaseAction);
					toolsMenu.add(changeCaseMenu);

					JMenu sortMenu = new LJMenu("sort",flp);
					sortMenu.add(ascendingSortAction);
					sortMenu.add(descendingSortAction);


					toolsMenu.add(sortMenu);

					toolsMenu.add(uniqueAction);
					toolsMenu.setEnabled(false);

					menuBar.add(toolsMenu);

					this.setJMenuBar(menuBar);
				}


				private JLabel length = new JLabel();


				private JLabel stats = new JLabel();

				/**Method used to update current caret status along with the length of the current document
				 * 
				 */
				private void setStats() {


					JTextArea text = model.getCurrentDocument().getTextComponent();
					length.setText(flp.getString("length")+": "+String.valueOf(text.getText().length()));
					Caret caret = text.getCaret();
					int ln=0;
					int col=0;
					int sel=0;
					try {
						ln = text.getLineOfOffset(caret.getDot())+1;
						col = caret.getDot()-text.getLineStartOffset(ln-1);
						sel = Math.abs(caret.getDot()-caret.getMark());
					} catch (BadLocationException e1) {

						e1.printStackTrace();
					}

					stats.setText(String.format("Ln:%d Col:%d Sel:%d", ln,col,sel));


				}
				
				private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				private JLabel clock = new JLabel(dateFormat.format(new Date()));

				
				private Timer timer = new Timer(1000,a->clock.setText(dateFormat.format(new Date())));

				/**Method used to create toolbars
				 * 
				 */
				private void createToolbars() {
					JToolBar toolBar = new JToolBar(flp.getString("toolbar"));
					toolBar.setFloatable(true);
					toolBar.add(new JButton(newDocumentAction));
					toolBar.add(new JButton(openDocumentAction));
					toolBar.add(new JButton(saveDocumentAction));
					toolBar.add(new JButton(saveAsDocumentAction));
					toolBar.add(new JButton(closeDocumentAction));
					toolBar.addSeparator();
					toolBar.add(copyAction);
					toolBar.add(cutAction);
					copyAction.setEnabled(false);
					cutAction.setEnabled(false);
					toolBar.add(new JButton(pasteAction));
					toolBar.add(new JButton(deleteSelectedPartAction));
					toolBar.add(new JButton(statisticalAction));

					this.getContentPane().add(toolBar, BorderLayout.PAGE_START);

					JToolBar status = new JToolBar();

					status.setFloatable(false);
					status.setLayout(new BorderLayout());
					status.add(length,BorderLayout.WEST);
					stats.setHorizontalAlignment(JLabel.CENTER);
					status.add(stats,BorderLayout.CENTER);
					status.add(clock,BorderLayout.EAST);
					setStats();
					timer.start();

					this.getContentPane().add(status,BorderLayout.PAGE_END);
				}

				/**Main method used to run the {@link JNotepadPP} app
				 * @param args
				 */
				public static void main(String[] args) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							new JNotepadPP().setVisible(true);
						}
					});
				}

}
