package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**Command that writes all available charsets to the shell. Used to gain information which charset to use
 * if not using the default one in {@link CatCommand}
 * @author Fran Kristijan Jelenčić
 *
 */
public class CharsetsCommand implements ShellCommand {
	private final static String NAME = "charsets";
	private final static List<String> DESCRIPTION = new ArrayList<String>();
	
	public CharsetsCommand() {
		// TODO Auto-generated constructor stub
		addDescription();
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		SortedMap<String, Charset> map =Charset.availableCharsets();
		map.forEach((k,v)->env.writeln(k));
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
		return Collections.unmodifiableList(DESCRIPTION);
	}
	
	private void addDescription() {
		DESCRIPTION.add("charsets command");
		DESCRIPTION.add("Command charsets takes no arguments and lists names of supported charsets for your Java platform.");
		DESCRIPTION.add("A single charset name is written per line.");
	}
}
