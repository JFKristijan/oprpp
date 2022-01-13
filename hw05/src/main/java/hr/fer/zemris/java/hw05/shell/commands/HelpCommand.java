package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**Command for outputting all supported commands and their descriptions to shell output
 * @author Fran Kristijan Jelenčić
 *
 */
public class HelpCommand implements ShellCommand {
	private final static String NAME = "help";
	private final static List<String> DESCRIPTION = new ArrayList<String>();

	public HelpCommand() {
		// TODO Auto-generated constructor stub
		addDescription();
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		if(arguments.length()==0) {
			env.writeln("Supported commands: ");
			SortedMap<String,ShellCommand> commands = env.commands();
			commands.forEach((k,v)->{
				//env.write("=".repeat(110)+"\n");
				v.getCommandDescription().forEach((c)->env.writeln(c));
				//env.write("=".repeat(110)+"\n");
			});
		}else {
			ShellCommand command = env.commands().get(arguments);
			if(command==null) {
				env.writeln("Specified command does not exist.");
			}else {
				command.getCommandDescription().forEach(env::writeln);
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return DESCRIPTION;
	}

	private void addDescription() {
		DESCRIPTION.add("help command");
		DESCRIPTION.add("Prints all the commands and their descriptions to the shell.");
	}
}
