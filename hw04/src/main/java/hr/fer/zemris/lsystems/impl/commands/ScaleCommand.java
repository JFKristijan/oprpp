package hr.fer.zemris.lsystems.impl.commands;


import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**Class that represents the Scale command
 *The factor is given in the constructor
 * @author Fran Kristijan Jelenčić
 *
 */
public class ScaleCommand implements Command {

	double factor;
	public ScaleCommand(double factor) {
		this.factor=factor;
	}
	/**Scales the current TurtleState by the given factor
	 *
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
	 ctx.getCurrentState().setLength(factor*ctx.getCurrentState().getLength());
	}

}
