package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.PathUtil;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**Command that reads given file and outputs its contents to the shell
 * If given 2 arguments the second argument determines which charset is used when outputting the contents
 * When given one argument (path to file to read) default JVM charset is used
 * @author Fran Kristijan Jelenčić
 *
 */
public class CatCommand implements ShellCommand {
	private final static String NAME = "cat";
	private final static List<String> DESCRIPTION = new ArrayList<String>();
	
	public CatCommand() {
		addDescription();
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		try {
			String[] args = PathUtil.parsePath(arguments);//arguments.trim().split("\\s+");
//			if(args.length>2) {     !!!questionable
//				env.writeln("You have specified more than 2 arguments.");
//				return ShellStatus.CONTINUE;
//			} 

			Path toFile = Path.of(args[0]);
			InputStream is = Files.newInputStream(toFile);
			byte[] buffer = new byte[1024];
			while(is.read(buffer, 0, 1024)!=-1) {
				env.write(new String(buffer,args.length==1?Charset.defaultCharset():Charset.forName(args[1])));
			}
			is.close();
			env.write("\n");
		}catch(IllegalCharsetNameException | UnsupportedCharsetException e) {

			env.writeln("You have specified a Charset that does not exist on this JVM");

		} catch (IOException e) {

			env.writeln("Error reading given file. Is the path correct?");
			
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
		DESCRIPTION.add("cat command");
		DESCRIPTION.add("This command opens given file and writes its content to console.");
		DESCRIPTION.add("Command cat takes one or two arguments. The first argument is path to some file and is mandatory.");
		DESCRIPTION.add("The second argument is charset name that should be used to interpret chars from bytes.");
		DESCRIPTION.add("If not provided, the default platform charset is used. (which is "+Charset.defaultCharset()+" in this instance)");
	}
}
