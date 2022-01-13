package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**Command used to exit MyShell
 * @author Fran Kristijan Jelenčić
 *
 */
public class ExitCommand implements ShellCommand {
	private final static String NAME = "exit";
	private final static List<String> DESCRIPTION = new ArrayList<String>();
	
	public ExitCommand() {
		// TODO Auto-generated constructor stub
		addDescription();
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(DESCRIPTION);
	}

	private void addDescription() {
		DESCRIPTION.add("exit command");
		DESCRIPTION.add("Terminates the shell");
	}
}
