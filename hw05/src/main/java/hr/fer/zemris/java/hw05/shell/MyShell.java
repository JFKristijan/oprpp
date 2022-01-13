package hr.fer.zemris.java.hw05.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw05.shell.commands.CatCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeCommand;

/**Class that is an implementation of a simple shell program that supports basic operations with files
 * @author Fran Kristijan Jelenčić
 *
 */
public class MyShell {
	public static void main(String[] args) {

		Environment env = new ShellEnvironment();
		SortedMap<String,ShellCommand> commands = env.commands();
		ShellStatus status = ShellStatus.CONTINUE;
		String line;
		ShellCommand command;

		env.writeln("Welcome to MyShell v 1.0");

		do {
			try {
				env.write(env.getPromptSymbol()+" ");
				line = env.readLine();

				String commandName = line.split("\\s+")[0];
				String arguments = line.substring(commandName.length()).trim();
				command = commands.get(commandName);
				if(command == null) {
					env.writeln("Unknown command");
					continue;
				}
				status = command.executeCommand(env, arguments);
			}catch(ShellIOException e) {
				command = commands.get("exit");
				status = command.executeCommand(env, "");
			}
		}while(status!=ShellStatus.TERMINATE);


	}
}
