package hr.fer.zemris.java.gui.layouts;

import java.awt.Dimension;

import java.util.Map;
import java.awt.Component;

/**Interface used for determinig dimensions of a component
 * @author Fran Kristijan Jelenčić
 *
 */
public interface ILayoutSize {
	
	Dimension getValue(Component c);
}
