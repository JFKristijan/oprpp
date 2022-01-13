package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**Command used for getting current PROMPT, MORELINES or MULTILINE symbols and updating them 
 * @author Fran Kristijan Jelenčić
 *
 */
public class SymbolCommand implements ShellCommand {
	private final static String NAME = "symbol";
	private final static List<String> DESCRIPTION = new ArrayList<String>();
	
	public SymbolCommand() {
		// TODO Auto-generated constructor stub
		addDescription();
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] args = arguments.split("\\s+");
		if(args.length>2||args.length==0) {
			env.writeln("Incorrect number of arguments");
		}
		if(args.length==1) {
			switch(args[0]) {
			
			case "PROMPT" -> env.writeln("Symbol for PROMPT is '"+env.getPromptSymbol()+"'");
			
			case "MORELINES" -> env.writeln("Symbol for MORELINES is '"+env.getMorelinesSymbol()+"'");
			
			case "MULTILINE" -> env.writeln("Symbol for MULTILINE is '"+env.getMultilineSymbol()+"'");
			
			default -> env.writeln("Unkown symbol");
			
			}
			
		}else {
			switch(args[0]) {
			case "PROMPT" -> {
				env.writeln("Symbol for PROMPT changed from '"+env.getPromptSymbol()+"' to '"+args[1]+"'");
				env.setPromptSymbol(args[1].charAt(0));
			}
			case "MORELINES" -> {
				env.writeln("Symbol for MORELINES changed from '"+env.getMorelinesSymbol()+"' to '"+args[1]+"'");
				env.setMorelinesSymbol(args[1].charAt(0));
			}
			case "MULTILINE" -> {
				env.writeln("Symbol for MULTILINE changed from '"+env.getMultilineSymbol()+"' to '"+args[1]+"'");
				env.setMultilineSymbol(args[1].charAt(0));
			}
			default -> env.writeln("Unkown symbol");
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
		return Collections.unmodifiableList(DESCRIPTION);
	}

	private void addDescription() {
		DESCRIPTION.add("symbol command");
		DESCRIPTION.add("If given one argument that is the name of the symbol prints to the shell the value");
		DESCRIPTION.add("of the given shell symbol. Update the symbol by making the second argument the value");
		DESCRIPTION.add("you wish the symbol to become.");
		
	}
}
