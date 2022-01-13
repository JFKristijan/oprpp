package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;

/**Interface that denotes an environment to be used in {@link MyShell} class
 * @author Fran Kristijan Jelenčić
 *
 */
public interface Environment {
	
	/**Reads a line from shell and returns it as a string
	 * @return String line read
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**Writes text to shell moving cursor to new line
	 * @param text
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**Writes text to shell, ending with a new line character
	 * @param text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**Method returns an unmodifiable SortedMap of commands
	 * @return SortedMap<String, ShellCommand>
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**Method returns current Shell Multiline symbol
	 * @return Character current Multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**Method used for setting a new Multiline symbol
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**Method returns current Shell Prompt symbol
	 * @return Character current prompt symbol
	 */
	Character getPromptSymbol();
	
	/**Method used for setting a new Prompt symbol
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**Method returns current Shell Morelines symbol
	 * @return Character current Morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**Method used for setting a new Morelines symbol
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);
	
}
