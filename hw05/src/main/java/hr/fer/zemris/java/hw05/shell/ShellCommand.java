package hr.fer.zemris.java.hw05.shell;

import java.util.List;

/**Interface used for creating commands to be executed in {@link MyShell}
 * @author Fran Kristijan Jelenčić
 *
 */
public interface ShellCommand {
	
	/**Method executed command that is specific to implementation in given environment with given arguments
	 * @param env environment the command needs to be executed in
	 * @param arguments for given command
	 * @return ShellStatus used for determining whether the shell should continue or exit
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**Method returns given command name
	 * @return string that represents command name
	 */
	String getCommandName();
	
	/**Method returns an unmodifiable List that contains the commands description
	 * @return List<String> containing command description
	 */
	List<String> getCommandDescription();
	
}
