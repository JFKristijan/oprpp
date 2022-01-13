package hr.fer.oprpp1.math;

/**Class that represents a 2D vector
 * @author Fran Kristijan Jelenčić
 *
 */
public class Vector2D {
	private double x;
	private double y;
	
	
	public Vector2D(double x,double y) {
		this.x=x;
		this.y=y;
	}
	
	/**Getter that returns the x value of this Vector2D object
	 * @return double x value of this object
	 */
	public double getX() {
		return x;
	}
	
	/**Getter that returns the y value of this Vector2D object
	 * @return double y value of this object
	 */
	public double getY() {
		return y;
	}
	
	/**Method that adds the given Vector2D object's x and y values to this one
	 * @param offset Vector2D that is to be added
	 */
	public void add(Vector2D offset) {
		this.x+=offset.x;
		this.y+=offset.y;
	}
	
	/**Method returns a Vector2d object after it adds the given Vector2D object's x and y values to this one 
	 * @param offset Vector2D that is to be added
	 * @return Vector2D object that is the result of addition
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x+offset.x,this.y+offset.y);
	}
	
	
	/**Method that rotates this vector by the angle given
	 * @param angle double that is the angle to rotate by
	 */
	public void rotate(double angle) {
		double x2 = Math.cos(angle)*x-Math.sin(angle)*y;
		double y2 = Math.sin(angle)*x+Math.cos(angle)*y;
		this.x=x2;
		this.y=y2;
	}
	
	/**Method that returns a new Vector2D object after it rotates this vector by the angle given
	 * @param angle double that is the angle to rotate by
	 * @return Vector2D object that is the result of rotation by the given angle degrees
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(Math.cos(angle)*x-Math.sin(angle)*y,Math.sin(angle)*x+Math.cos(angle)*y);
	}
	
	/**Method that scales this vectors x and y components
	 * @param scalar that is used for scaling the components
	 */
	public void scale(double scalar) {
		x*=scalar;
		y*=scalar;
		
	}
	
	/**Method that returns a new Vector2D object after it scales this vectors x and y components
	 * @param scalar that is used for scaling the components
	 * @return Vector2D object that is the result of scaling bt the scalar
	 */
	public Vector2D scaled(double scalar) {
		return new Vector2D(x*scalar,y*scalar);
	}
	
	
	/**Method that creates a copy of this vector
	 * @return Vector2D that is a copy of the vector that this method is called on
	 */
	public Vector2D copy() {
		return new Vector2D(x,y);
	}
	
	/**Returns a boolean value true if the given object is equal to this one. false otherwise
	 *
	 *@return true if this Vector2D object has the same x and y values as the given object, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null)return false;
		if(!(obj instanceof Vector2D))return false;
		Vector2D temp = (Vector2D) obj;
		return Math.abs(this.x-temp.x)<0.0001&&Math.abs(this.y-temp.y)<0.0001;
	}
}
