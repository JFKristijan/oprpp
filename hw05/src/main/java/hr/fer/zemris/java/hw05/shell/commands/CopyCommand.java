package hr.fer.zemris.java.hw05.shell.commands;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.PathUtil;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**Command used for copying files
 * Takes 2 arguments path to source file and path to destination file, if second path is to a directory
 * the method copies the source file to destination while keeping the original name
 * If destination file exists user is asked whether they wish to overwrite it
 * @author Fran Kristijan Jelenčić
 *
 */
public class CopyCommand implements ShellCommand {
	private final static String NAME = "copy";
	private final static List<String> DESCRIPTION = new ArrayList<String>();

	public CopyCommand() {
		addDescription();
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		//.split("\\s+");

		try {
			String[] args = PathUtil.parsePath(arguments);
			Path source = Paths.get(args[0]);
			Path destination = Paths.get(args[1]);

			if(source.toFile().isDirectory()) {
				env.writeln("Given source file is a directory. Aborting operation...");
				return ShellStatus.CONTINUE;
			}

			if(destination.toFile().exists()&&destination.toFile().isDirectory()) {
				destination = Paths.get(args[1]+"\\"+source.getFileName());
			}

			if(destination.toFile().exists()&&destination.toFile().isFile()) {
				env.writeln("File already exists at given destination.\nWould you like to overwrite? y\\n");
				String res = env.readLine().toLowerCase();
				if(!res.equals("y")) {
					env.writeln("Operation cancelled.");
					return ShellStatus.CONTINUE;
				}
			}

			InputStream is = Files.newInputStream(source);
			OutputStream os = Files.newOutputStream(destination);
			int read;
			byte[] buffer = new byte[1024];
			while((read = is.read(buffer))!= -1) {
				os.write(buffer,0,read);
			}
			is.close();
			os.close();

		}catch(Exception e) {
			env.writeln("An error occurred during the copy operation.");
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
		return Collections.unmodifiableList(DESCRIPTION);
	}

	private void addDescription() {
		DESCRIPTION.add("copy command");
		DESCRIPTION.add("The copy command expects two arguments: source file name and destination file name (i.e. paths and names)");
		DESCRIPTION.add("If destination file exists, user is asked whether they want to rewrite it.");
	}



}
