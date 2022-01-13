package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.PathUtil;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**Command that prints a tree from given path that is a directory name
 * @author Fran Kristijan Jelenčić
 *
 */
public class TreeCommand implements ShellCommand {
	private final static String NAME = "tree";
	private final static List<String> DESCRIPTION = new ArrayList<String>();
	
	public TreeCommand() {
		addDescription();
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length()==0) {
			arguments = ".";
		}
		try {
			
			String[] path = PathUtil.parsePath(arguments); 
			if(path.length>1&&!path[1].equals("")) {
				env.writeln("Too many arguments");
				return ShellStatus.CONTINUE;
			}
			
			Path toWalk = Paths.get(path[0]);
			if(!toWalk.toFile().isDirectory()) {
				env.writeln("You have specified a file, not a directory");
				return ShellStatus.CONTINUE;
			}
			
			Files.walkFileTree(Paths.get(path[0]), new FileVisitor<Path>() {
				int indent = 0;
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					env.writeln(" ".repeat(indent*2)+dir.getFileName().toString());
					indent++;
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					env.writeln(" ".repeat(indent*2)+file.getFileName().toString());
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					indent--;
					return FileVisitResult.CONTINUE;
				}
			});
		} catch(IllegalArgumentException e) {
			
			env.writeln(e.getMessage());
			
		}catch (Exception e) {

			env.writeln("An error occurred during walking the file tree");
			
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
		DESCRIPTION.add("tree command");
		DESCRIPTION.add("The tree command expects a single argument: directory name and prints a tree (each directory level shifts output two charatcers to the right).");
	}

}
