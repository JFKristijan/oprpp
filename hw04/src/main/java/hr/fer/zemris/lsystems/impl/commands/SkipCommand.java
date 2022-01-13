package hr.fer.zemris.lsystems.impl.commands;


import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**Class that represents the Skup command
 *The step is given in the constructor
 * @author Fran Kristijan Jelenčić
 *
 */
public class SkipCommand implements Command {
	double step;
	public SkipCommand(double step) {
		this.step=step;
	}
	/**Moves current TurtleState to current TurtleState + angle * step * length vector
	 *
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D first = state.getVector();
		double x1 = first.getX();
		double y1 = first.getY();
		first.add(state.getAngle().scaled(step*state.getLength()));
		double x2 = first.getX();
		double y2 = first.getY();
		painter.drawLine(x1,y1, x2, y2, state.getColor(), 1f);
	}

}
