package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.PathUtil;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**Command that writes directory listing (not recursive)
 *First column indicates if current object is directory (d), readable (r),writable (w) and executable (x).
 *Second column contains object size in bytes that is right aligned andoccupies 10 characters.
 * Third column is file creation date/time and finally 
 * Fourth column is file name.
 * @author Fran Kristijan Jelenčić
 *
 */
public class LsCommand implements ShellCommand {
	private final static String NAME = "ls";
	private final static List<String> DESCRIPTION = new ArrayList<String>();

	public LsCommand() {
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
			
			File dir = new File(path[0]);
			if(!dir.isDirectory()) {
				env.writeln("Given path is not a directory");
				return ShellStatus.CONTINUE;
			}
			File[] children = dir.listFiles();

			if(children!=null) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				for(File f: children) {
					BasicFileAttributeView faView = Files.getFileAttributeView(
							f.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
							);
					BasicFileAttributes attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();

					String isDirectory = f.isDirectory() ? "d" : "-";
					String isReadable = f.canRead() ? "r" : "-";
					String isWriteable = f.canWrite() ? "w" : "-";
					String isExecutable = f.canExecute() ? "x" : "-";
					long size = 0;
					if(!f.isDirectory()) {
						size = f.length();
					}else {
						size = getDirSize(f);
					}
					String name = f.getName();
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
					;
					env.writeln(String.format("%s%s%s%s %10d %s %s", isDirectory, isReadable,
							isWriteable, isExecutable,
							size, formattedDateTime, name));
				}
			}


		}catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
		}catch(Exception e) {

		}
		return null;

	}

	private long getDirSize(File directory) {
		long size = 0;
		for (File file : directory.listFiles()) {
			if (file.isFile())
				size += file.length();
			else
				size += getDirSize(file);
		}
		return size;
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
		DESCRIPTION.add("ls command");
		DESCRIPTION.add("Command ls takes a single argument – directory – and writes a directory listing (not recursive).");
		DESCRIPTION.add("If no argument is given writes a directory listing of the directory the program is being run from. (not recursive)");
	}
}

//
//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//Path path = Paths.get("C:\\Users\\frank\\Downloads\\RECORDS");
//BasicFileAttributeView faView = Files.getFileAttributeView(
//		path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
//		);
//BasicFileAttributes attributes = faView.readAttributes();
//FileTime fileTime = attributes.creationTime();
//String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
