package hr.fer.zemris.java.gui.calc.model;

import java.awt.event.ActionListener;

import javax.swing.JButton;


/**Class for buttons that can have inverted functions and another name
 * @author Fran Kristijan Jelenčić
 *
 */
public class CalcInvertableButton extends JButton {
	private static final long serialVersionUID = 1L;
	private String text;
	private String inverseText;
	private ActionListener defaultAction;
	private ActionListener invertedAction;
	private boolean inverted = false;
	
	/**Constructor for CalcInvertableButton 
	 * @param text default text
	 * @param inverseText text when inverted
	 * @param defaultAction default action 
	 * @param invertedAction action when inverted
	 */
	public CalcInvertableButton(String text, String inverseText, ActionListener defaultAction, ActionListener invertedAction) {
		super();
		this.text = text;
		this.inverseText = inverseText;
		this.defaultAction = defaultAction;
		this.invertedAction = invertedAction;
		this.addActionListener(defaultAction);
		this.setText(text);
		this.setOpaque(true);
		this.setFont(this.getFont().deriveFont(15f));
		
	}
	
	/**Sets the button text to alternative along with the action listener
	 * 
	 */
	public void invert() {
		this.setText(inverted?text:inverseText);
		this.removeActionListener(inverted?invertedAction:defaultAction);
		this.addActionListener(inverted?defaultAction:invertedAction);
		inverted=!inverted;
	}
	

}
