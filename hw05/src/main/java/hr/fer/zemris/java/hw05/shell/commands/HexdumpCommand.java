package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.crypto.Util;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.PathUtil;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**Command that reads a file and prints its hex representation to the shell in formatted form
 * All characters less than 32 and greater than 127 are replaced with '.'
 * @author Fran Kristijan Jelenčić
 *
 */
public class HexdumpCommand implements ShellCommand {
	private final static String NAME = "hexdump";
	private final static List<String> DESCRIPTION = new ArrayList<String>();
	
	public HexdumpCommand() {
		// TODO Auto-generated constructor stub
		addDescription();
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		//Path file = Paths.get(arguments);
		try {
			String[] path = PathUtil.parsePath(arguments); 
			if(path.length>1&&!path[1].equals("")) {
				env.writeln("Too many arguments");
				return ShellStatus.CONTINUE;
			}
			
			Path file = Paths.get(path[0]);
			InputStream is = Files.newInputStream(file);
			
			int index=0;
			byte[] buffer = new byte[16];
			StringBuilder sb = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			//vise mi se svida ispis sa 00 ako nema podataka u tom redu pa cu to ostaviti tako
			while((is.read(buffer))!=-1) {
				
				sb.append(String.format("%08X: ", index));
				String hex = Util.bytetohex(buffer).toUpperCase();
				
				for(int i = 0 ; i < 16 ; i++) {
					if(i==7) {
						sb.append(hex.substring(i,i+2)+"|");
					}else {
						sb.append(hex.substring(i,i+2)+" ");
					}
					sb2.append(buffer[i]<32||buffer[i]>127?".":(char) buffer[i]);
				}
				
				sb.append("| "+sb2.toString());
				index+=16;//hex 10
				
				env.writeln(sb.toString());
				
				sb.setLength(0);
				sb2.setLength(0);
			}

		} catch (IOException e) {

			env.writeln("An error occurred during reading file.");

		}catch(StringIndexOutOfBoundsException e) {
			
			env.writeln("Error while parsing path please check input");
		}catch(Exception e) {
			
			env.writeln("An error has occurred");
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
		DESCRIPTION.add("hexdump command");
		DESCRIPTION.add("The hexdump command expects a single argument: file name, and produces hex-output");
	}
}
