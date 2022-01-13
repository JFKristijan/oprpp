package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;







/**Class that represents a complex number implementation
 * 
 * @author Fran Kristijan Jelenčić
 *
 */
public class Complex {
	private double real;
	private double imaginary;
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);




	/**Constructor for a new <code>Complex</code> object with imaginary and real values set to 0
	 * 
	 */
	public Complex() {
		real=0;
		imaginary=0;
	}

	/**Contructor for a new <code>Complex</code> object with imaginary and real values set through the parameters
	 * @param re Real part
	 * @param im Imaginary part
	 */
	public Complex(double re, double im) {
		this.real=re;
		this.imaginary=im;
	}

	/**Method that parses given string into a new <code>Complex</code> object
	 * @param s String to parse from
	 * @return Complex object from given string
	 */
	public static Complex parse(String s) {
		s=s.toLowerCase();
		if(s.startsWith("+"))s=s.substring(1);
		//if(!s.endsWith("i")) return new Complex(Double.parseDouble(s), 0);
		//if(s.startsWith("i")) return new Complex(0, 1);
		if(s.startsWith("-i")) return new Complex(0, -1);
		boolean prvi = false;
		if(s.startsWith("-")) {
			prvi=true;
			s=s.substring(1);
		}
		String[] split = s.split("[+-]");
		boolean minus = s.indexOf("-")!=-1?true:false;
		try {
			if(split.length==1) {
				if(split[0].contains("i")) {
					split[0]=split[0].replace("i", "");
					return new Complex(0, Double.parseDouble((prvi?"-":"")+(split[0].equals("")?"1":split[0])));
				}else {
					return new Complex( Double.parseDouble((prvi?"-":"")+split[0]),0);
				}
			}
			if(split[0].contains("i")) {
				split[0]=split[0].replace("i","");
				return new Complex( Double.parseDouble((minus?"-":"")+(split[1].equals("")?"1":split[1])),Double.parseDouble((prvi?"-":"")+(split[0].equals("")?"1":split[0])));
			}else {
				split[1]=split[1].replace("i", "");
				return new Complex(Double.parseDouble((prvi?"-":"")+split[0]), Double.parseDouble((minus?"-":"")+(split[1].equals("")?"1":split[1])));
			}
		}catch(Exception e) {
			throw new IllegalArgumentException("The given string: '"+s+"' is not parsable");
		}

	}

	/**Method that returns the real part of <code>Complex</code> object
	 * @return real part of this <code>Complex</code> object
	 */
	public double getReal() {
		return real;
	}

	/**Method that returns the imaginary part of <code>Complex</code> object
	 * @return imaginary part of this <code>Complex</code> object
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**Method that calculates the module of this <code>Complex</code> object
	 * @return value of module
	 */
	public double module() {
		return Math.sqrt(real*real+imaginary*imaginary);
	}


	/**Method that multiplies this object with another <code>Complex</code> object and returns a new <code>Complex</code> object as result
	 * @param c <code>Complex</code> number to multiply with
	 * @return new <code>Complex</code> object that is the result of multiplication
	 */
	public Complex multiply(Complex c) {
		double re = this.real * c.real - this.imaginary * c.imaginary;
		double imag = this.imaginary * c.real + c.imaginary * this.real;
		return new Complex(re, imag);
	}




	/**Method that divides this object with another <code>Complex</code> object and returns a new <code>Complex</code> object as result
	 * @param c <code>Complex</code> number to divide with
	 * @return new <code>Complex</code> object that is the result of division
	 */
	public Complex divide(Complex c) {
		double re = (this.real*c.real+this.imaginary*c.imaginary)/(c.real*c.real+c.imaginary*c.imaginary);
		double imag = (this.imaginary*c.real-this.real*c.imaginary)/(c.real*c.real+c.imaginary*c.imaginary);
		return new Complex(re, imag);
	}


	/**Method that adds this object with another <code>Complex</code> object and returns a new <code>Complex</code> object as result
	 * @param c <code>Complex</code> number to add to this
	 * @return new <code>Complex</code> object that is the result of addition
	 */
	public Complex add(Complex c) {		
		real = this.real + c.real;
		imaginary = this.imaginary + c.imaginary;
		return new Complex(real, imaginary);
	}


	/**Method that subtracts this object with another <code>Complex</code> object and returns a new <code>Complex</code> object as result
	 * @param c <code>Complex</code> number to subtract from this
	 * @return new <code>Complex</code> object that is the result of subtraction
	 */
	public Complex sub(Complex c) {		
		double realRet = this.real - c.real;
		double imaginaryRet = this.imaginary - c.imaginary;
		return new Complex(realRet, imaginaryRet);}


	/**Method that negates this <code>Complex</code> object and returns a new <code>Complex</code> object 
	 * Real part = - real part
	 * Imaginary part = - imaginary part
	 * @return new negated <code>Complex</code> object
	 */
	public Complex negate() {
		return new Complex(-real,-imaginary);
	}


	/**Method that raises this <code>Complex</code> object to the n-th power
	 * @param n to which power to raise
	 * @return new <code>Complex</code> object that is the result of raising this <code>Complex</code> object to the n-th power
	 */
	public Complex power(int n) {
		//z^n = r^n * ( cos(nTheta) + isin(nTheta))
		double r = Math.pow(module(),n);
		double angle = Math.atan2(imaginary, real)*n;
		return new Complex(r*Math.cos(angle),r*Math.sin(angle));
	}

	/**Calculates the roots of the <code>Complex</code>
	 * @param n which root to calculate of the <code>Complex</code>
	 * @return List of <code>Complex</code> that are the solutions of rooting
	 */
	public List<Complex> root(int n) {
		List<Complex> retArr = new ArrayList<Complex>();
		double r = Math.pow(Math.sqrt(real*real+imaginary*imaginary), 1.0/n);
		double theta = Math.atan2(imaginary, real);
		for(int i = 0 ; i < n;i++) {
			Complex num = new Complex(r*Math.cos(theta + 2 * Math.PI * i ),r*Math.sin(theta + 2 * Math.PI * i ));
			retArr.add(num);
		}

		return retArr;
	}

	/**Returns a string representation of the object
	 *
	 */
	@Override
	public String toString() {
		return "("+real+(imaginary>=0?"+i":"-i")+Math.abs(imaginary)+")";
	}

}
