package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**Interface that models a command
 * @author Fran Kristijan Jelenčić
 *
 */
public interface Command {
	void execute(Context ctx,Painter painter);
}
