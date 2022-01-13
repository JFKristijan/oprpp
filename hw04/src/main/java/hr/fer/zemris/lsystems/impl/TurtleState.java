package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;

/**Class that represents a turtle state, keeps information about angle, location, color and length
 * @author Fran Kristijan Jelenčić
 *
 */
public class TurtleState {
	private Vector2D angle = new Vector2D(1,0);
	private Vector2D location = new Vector2D(0,0);
	private Color color = Color.BLACK;
	private double length;
	public TurtleState() {}
	public TurtleState(Vector2D origin,Vector2D angle,Color color, double length) {
		this.location=origin;
		this.angle=angle;
		this.color=color;
		this.length=length;
	}
	/**Rotates the turtle by given degrees
	 * @param angle
	 */
	public void rotate(double angle) {
		this.angle.rotate(angle);
	}
	/**Sets the color of the turtle 
	 * @param color to set the turtle to
	 */
	public void color(Color color) {
		this.color=color;
	}
	/**Copies the current TurtleState object
	 * @return TurtleState that is an exact copy of this TurtleState
	 */
	public TurtleState copy() {
		return new TurtleState(location.copy(),angle.copy(),color,length);
	}
	
	/**Getter for angle
	 * @return Vector2D angle
	 */
	public Vector2D getAngle() {
		return angle;
	}
	/**Getter for location
	 * @return Vector2D location of the turtle
	 */
	public Vector2D getVector() {
		return location;
	}
	/**Getter for color
	 * @return Color color of the turtle
	 */
	public Color getColor() {
		return color;
	}

	/**Getter for length
	 * @return  length
	 */
	public double getLength() {
		return length;
	}
	
	/**Setter for length 
	 * @param length to set
	 */
	public void setLength(double length) {
		this.length=length;
	}
	
}
