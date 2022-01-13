package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**Class that represents the Draw command
 *The step is given in the constructor
 * @author Fran Kristijan Jelenčić
 */
public class DrawCommand implements Command{
	double step;
	public DrawCommand(double step) {
		this.step=step;
	}
	/**Draws a line between two points calculated from current location to current TurtleState + angle * step * length
	 *
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D first = state.getVector();
		double x1 = first.getX();
		double y1 = first.getY();
		first.add(state.getAngle().copy().scaled(step*state.getLength()));
		double x2 = first.getX();
		double y2 = first.getY();
		painter.drawLine(x1,y1, x2, y2, state.getColor(), 1f);
	}

}
