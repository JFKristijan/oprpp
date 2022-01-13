package hr.fer.zemris.java.hw05.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw05.shell.commands.CatCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw05.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw05.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw05.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeCommand;

/**Implementation of an {@link Environment} used in {@link MyShell} class
 * @author Fran Kristijan Jelenčić
 *
 */
public class ShellEnvironment implements Environment{
	
	private Character promptSymbol = '>';
	private Character moreLineSymbol = '\\';
	private Character multiLineSymbol = '|';
	private SortedMap<String, ShellCommand> commands;
	private Scanner sc; //kako ovo closeat??!?

	/**Constructor for ShellEnvironment
	 * Initalizes an Environment instance with commands and opens scanner
	 * 
	 */
	public  ShellEnvironment() {
		sc = new Scanner(System.in);
		commands = new TreeMap<String, ShellCommand>();
		
		ShellCommand command = new CharsetsCommand();
		commands.put(command.getCommandName(), command);
		
		command = new CatCommand();
		commands.put(command.getCommandName(), command);
		
		command = new LsCommand();
		commands.put(command.getCommandName(), command);
		
		command = new TreeCommand();
		commands.put(command.getCommandName(), command);
		
		command = new CopyCommand();
		commands.put(command.getCommandName(), command);
		
		command = new MkdirCommand();
		commands.put(command.getCommandName(), command);
		
		command = new HexdumpCommand();
		commands.put(command.getCommandName(), command);
		
		command = new SymbolCommand();
		commands.put(command.getCommandName(), command);
		
		command = new ExitCommand();
		commands.put(command.getCommandName(), command);
		
		command = new HelpCommand();
		commands.put(command.getCommandName(), command);
	}
	
	
	@Override
	public void writeln(String text) throws ShellIOException {
		// TODO Auto-generated method stub
		try {
			System.out.println(text);
		}catch(Exception e) {
			throw new ShellIOException();
		}

	}

	@Override
	public void write(String text) throws ShellIOException {
		// TODO Auto-generated method stub
		try {
			System.out.print(text);
		}catch(Exception e) {
			throw new ShellIOException();
		}
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		// TODO Auto-generated method stub
		promptSymbol = symbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		// TODO Auto-generated method stub
		multiLineSymbol = symbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		// TODO Auto-generated method stub
		moreLineSymbol=symbol;
	}

	@Override
	public String readLine() throws ShellIOException {
		try {
			String readstr = sc.nextLine();
			
			while(readstr.endsWith(moreLineSymbol.toString())) {
				readstr = readstr.replace(moreLineSymbol.toString(), "");
				write(multiLineSymbol+" ");
				readstr+=sc.nextLine();
			}
			
			//readstr = readstr.replace(moreLineSymbol.toString(), "");
			
			return readstr;

		}catch(Exception e) {
			throw new ShellIOException();
		}

	}

	@Override
	public Character getPromptSymbol() {
		// TODO Auto-generated method stub
		return promptSymbol;
	}

	@Override
	public Character getMultilineSymbol() {
		// TODO Auto-generated method stub
		return multiLineSymbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		// TODO Auto-generated method stub
		return moreLineSymbol;
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {

		return Collections.unmodifiableSortedMap(commands);

	}
}
