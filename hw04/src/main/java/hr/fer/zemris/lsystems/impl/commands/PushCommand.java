package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
/**Class that represents the Push command
 * @author Fran Kristijan Jelenčić
 *
 *
 */
public class PushCommand implements Command {

	/**Pushes the current state to context stack
	 *
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
