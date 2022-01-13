package hr.fer.zemris.math;



/**Class that represents a Complex rooted polynomial
 * @author Fran Kristijan Jelenčić
 *
 */
public class ComplexRootedPolynomial {
	private Complex constant;
	private Complex[] roots;

	/**Contructor for a new {@link ComplexRootedPolynomial} object
	 * @param constant the constant to use
	 * @param roots the roots of the rooted polynomial
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	
	/**Calulates the value of this {@link ComplexRootedPolynomial} object at point z
	 * @param z the point at which to calculate the value at
	 * @return new Complex object that is the value of this {@link ComplexRootedPolynomial} at point z
	 */
	public Complex apply(Complex z) {
		Complex retVal = constant;
		for(int i = 0; i < roots.length ; i++) {
			retVal = retVal.multiply(z.sub(roots[i]));
		}
		return retVal;
	}



	/**Returns the string representation of this object
	 *
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant.toString());
		for(Complex c: roots) {
			sb.append("*(z-"+c.toString()+")");
		}
		return sb.toString();
	}

	/**Calculate the index of closest root for Complex number z and given threshold
	 * @param z Complex number from which to calculate the closest index for
	 * @param threshold epsilon 
	 * @return index of closest root, -1 if no roots satsisfy threshold
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		int index = -1;
		for(int i = 0 ; i < roots.length ; i++) {
			double distance = z.sub(roots[i]).module();
//					new Complex(Math.pow(z.getReal()-roots[i].getReal(), 2),
//					Math.pow(z.getImaginary()-roots[i].getImaginary(), 2)).module();
			if(distance<threshold) {
				index=i;
				break;// zadnji index ili prvi?
			}
		}
		return index;
	}
	
	
	/**Turns this object into its {@link ComplexPolynomial} version
	 * @return new {@link ComplexPolynomial} that is generated from this {@link ComplexRootedPolynomial}
	 */
	public ComplexPolynomial toComplexPolynom() {
		//z0*(z-z1)*(z-z2)*(z-z3) ...
		ComplexPolynomial retVal = new ComplexPolynomial(constant);
		for(int i = 0 ; i < roots.length ; i++) {
			retVal = retVal.multiply(
					new ComplexPolynomial(roots[i].negate(),Complex.ONE)
					);
		}
		return retVal;
	}
















}
