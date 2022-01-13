package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**Class used to represent a model for a bar chart
 * @author Fran Kristijan Jelenčić
 *
 */
public class BarChart {
	private List<XYValue> values;
	private String opisX;
	private String opisY;
	private int yMin;
	private int yMax;
	private int step;
	
	/**Constructor for BarChart
	 * @param values list of XYValues
	 * @param opisX description for X axis
	 * @param opisY description for Y axis
	 * @param yMin minimum value for Y axis
	 * @param yMax maximum value for Y axis
	 * @param step step used for incrementing Y axis values 
	 */
	public BarChart(List<XYValue> values, String opisX, String opisY, int yMin, int yMax, int step) {
		if(yMin<0)
			throw new IllegalArgumentException("Y minimum value cannot be less than 0");
		if(yMax<=yMin)
			throw new IllegalArgumentException("Y maximum value cannot be less than Y minimum value");
		if(values.stream().map(v->v.getY()).min(Integer::compareTo).get()<yMin) 
			throw new IllegalArgumentException("Y value of values cannot be less than Y minimum");
		if(values.stream().map(v->v.getY()).max(Integer::compareTo).get()>yMax)
			throw new IllegalArgumentException("Y value of values cannot be more than Y maximum");
		this.values = values;
		this.opisX = opisX;
		this.opisY = opisY;
		this.yMin = yMin;
		this.yMax = yMax;
		this.step=step;
	}

	/**Getter for Values
	 * @return list of XYvalues
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**Getter for OpisX
	 * @return description of x axis
	 */
	public String getOpisX() {
		return opisX;
	}
	
	/**Getter for OpisY
	 * @return description of y axis
	 */
	public String getOpisY() {
		return opisY;
	}

	/**Getter for y minimal value
	 * @return minimum value of y axis
	 */
	public int getyMin() {
		return yMin;
	}
	
	/**Getter for y minimal value
	 * @return minimum value of y axis
	 */
	public int getyMax() {
		return yMax;
	}
	/**Getter for y minimal value
	 * @return  value of step
	 */
	public int getStep() {
		return step;
	}
	
	
}
