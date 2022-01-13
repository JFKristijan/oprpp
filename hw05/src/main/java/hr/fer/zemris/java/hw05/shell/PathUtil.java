package hr.fer.zemris.java.hw05.shell;

/**Class used for parsing paths
 * @author Fran Kristijan Jelenčić
 *
 */
public class PathUtil {

	/**Parses new String[] from given args, splits by spaces if not in literals
	 * @param args
	 * @return String[]
	 */
	public static String[] parsePath(String args) {
		String[] ret;
		if(args.contains("\"")) {
			ret = new String[2];
			if(args.startsWith("\"")) {
				ret[0]=getInsideLiterals(args);
				args=args.substring(args.indexOf("\"", 1)+1).trim();
			}else {
				int index=args.indexOf(" ");
				//if(index<0)
				//	index=args.length();		
				ret[0]=args.substring(0,index);
				args=args.substring(index).trim();
			}
			if(args.startsWith("\"")) {
				ret[1]=getInsideLiterals(args);
			}else {
				if(args.contains("\"")) {
					throw new IllegalArgumentException("Improper format of arguments");
				}
				ret[1]=args;
			}
		}else {
			ret=args.split("\\s+");
		}
		
		return ret;
	}
	
	/**Method returns String that is contained in literals
	 * @param str
	 * @return String from inside literals
	 */
	public static String getInsideLiterals(String str) {
		int index = 1;
		for(int i = 1 ; i < str.length() ; i++,index++) {
			if(str.charAt(i)=='\"' && str.charAt(i-1)!='\\') {
				break;
			}
		}
		if(index==str.length()) {
			throw new IllegalArgumentException("Literals not closed...");
		}
		if(index+1<str.length()&&str.charAt(index+1)!=' ') {
			throw new IllegalArgumentException("Improper arguments given. Please check for typos");
		}

		return str.substring(1, index);
	}
}
