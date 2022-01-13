package hr.fer.zemris.java.gui.layouts;

/**Class used for positioning elements in CalcLayout
 * @author Fran Kristijan Jelenčić
 *
 */
public class RCPosition {
	private int row;
	private int column;


	/**Getter for row
	 * @return
	 */
	public int getRow() {
		return row;
	}


	/**Hetter for column
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**Constructor with two parameters row and column
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row=row;
		this.column=column;
	}

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}


	/**Parses the RCPosition object from given string
	 * @param str
	 * @return RCPosition denoted by the string
	 */
	public static RCPosition parse(String str) {
		String[] split = str.split(",");
		if(split.length!=2) {
			throw new CalcLayoutException("Please supply 2 integers separated by a ','");
		}

		return new RCPosition(Integer.parseInt(split[0]),Integer.parseInt(split[1]));

	}
}
