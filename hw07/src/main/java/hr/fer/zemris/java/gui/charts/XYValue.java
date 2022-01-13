package hr.fer.zemris.java.gui.charts;

/**Class used to store X and Y values 
 * @author Fran Kristijan Jelenčić
 *
 */
public class XYValue {
	private int x;
	private int y;
	
	
	
	
	/**Getter for X value
	 * @return x value
	 */
	public int getX(){
		return x;
	}
	/**Getter for y value
	 * @return y value
	 */
	public int getY() {
		return y;
	}
	protected XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
}
