package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
/**Class that represents the Pop command
 * @author Fran Kristijan Jelenčić
 *
 */
public class PopCommand implements Command {

	/**
	 *Pops a state from context stack
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
