package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;


import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;


/**Class that represents the Color command
 *The color is given in the constructor
 * @author Fran Kristijan Jelenčić
 *
 */
public class ColorCommand implements Command {
	Color color;
	public ColorCommand(Color color) {
		this.color=color;
	}
	/**Sets the TurtleStates color to the chosen color
	 *
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().color(color);
	}

}
