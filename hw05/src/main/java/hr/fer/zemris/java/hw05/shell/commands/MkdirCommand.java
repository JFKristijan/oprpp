package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.PathUtil;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**Command that creates a directory at given path which is given as the commands only arguments
 * @author Fran Kristijan Jelenčić
 *
 */
public class MkdirCommand implements ShellCommand {
	private final static String NAME = "mkdir";
	private final static List<String> DESCRIPTION = new ArrayList<String>();
	
	public MkdirCommand() {
		// TODO Auto-generated constructor stub
		addDescription();
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		//Path dir = Paths.get(arguments);

		try {
			String[] path = PathUtil.parsePath(arguments); 
			
			if(path.length>1&&!path[1].equals("")) {
				env.writeln("Too many arguments");
				return ShellStatus.CONTINUE;
			}
			
			Path dir = Paths.get(path[0]);
			Files.createDirectories(dir);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			env.writeln("An error occurred while creating directory");
		}catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
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
		DESCRIPTION.add("mkdir command");
		DESCRIPTION.add("The mkdir command takes a single argument: directory name, and creates the appropriate directory structure");
	}
}
