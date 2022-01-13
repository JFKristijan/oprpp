package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
/**Class that represents the Rotate command
 *The angle is given in the constructor
 * @author Fran Kristijan Jelenčić
 *
 */
public class RotateCommand implements Command {
	double angle;
	public RotateCommand(double angle) {
		this.angle=angle;
	}
	/**Rotates the current state by the angle given in the constructor
	 *
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().rotate(Math.toRadians(angle));

	}

}
