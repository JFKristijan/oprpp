package hr.fer.oprpp1.hw01;


public class ComplexNumber {
	private double real;
	private double imaginary;

	/**Constructor for ComplexNumber that accepts two doubles
	 * @param real part of the complex number
	 * @param imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real=real;
		this.imaginary=imaginary;
	}
	/**Returns a ComplexNumber object from only real value, imaginary part is set to 0
	 * @param real part of the complex number
	 * @return new ComplexNumber object with real part and imaginary part set to 0
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real,0);
	}
	/**Returns a ComplexNumber object with only imaginary value, real part is set to 0
	 * @param imaginary part of the complex number
	 * @return new ComplexNumber object with imaginary part and real part set to 0
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0,imaginary);
	}

	/**Returns a ComplexNumber object from magnitude and angle
	 * @param magnitude of the ComplexNumber to be created
	 * @param angle of the ComplexNumber to be created
	 * @return new ComplexNumber with the given parameters
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,double angle) {
		return new ComplexNumber(magnitude*Math.cos(angle),magnitude*Math.sin(angle));
	}

	/**Creates a ComplexNumber object by parsing arguments from given string
	 * @param s String that will be parsed that contains arguments for the ComplexNumber
	 * @return new ComplexNumber with arguments parsed from the given string
	 * @throws IllegalArgumentException when the given string is not parsable
	 */
	public static ComplexNumber parse(String s) {
		s=s.toLowerCase().replace("\\s+", "");
		if(s.startsWith("+"))s=s.substring(1);
		if(!s.endsWith("i")) return new ComplexNumber(Double.parseDouble(s), 0);
		if(s.startsWith("i")) return new ComplexNumber(0, 1);
		if(s.startsWith("-i")) return new ComplexNumber(0, -1);


		StringBuilder sb = new StringBuilder();
		String[] expression = new String[2];
		int index=0;
		char[] data = s.toCharArray();
		int i = 0;
		boolean minus=false;
		while(i<data.length) {
			if( (data[i]!='+'&&data[i]!='-') || (i==0) ||
					(data[i]=='-'&&data[i-1]=='e') ||
					(data[i]=='+'&&data[i-1]=='e')) {
				sb.append(data[i]);
			}else {

				expression[index++] = sb.toString();
				sb.setLength(0);
				if(data[i]=='-')
					minus=true;
			}
			i++;
		}
		if(!sb.isEmpty()) {
			expression[index++]=sb.toString();
		}
		try {
			if(index==1) {
				return new ComplexNumber(0, Double.parseDouble((minus?"-":"")+expression[0].replace("i", "")));
			}
			expression[1]=expression[1].replace("i", "");
			return new ComplexNumber(Double.parseDouble(expression[0]), Double.parseDouble((minus?"-":"")+(expression[1].equals("")?"1":expression[1])));
		}catch(Exception e) {
			throw new IllegalArgumentException("The given string: '"+s+"' is not parsable");
		}


		//		boolean first = false;
		//		if(s.startsWith("-")) {
		//			first=true;
		//			s=s.substring(1);
		//		}
		//		String[] split = s.split("[+-]");
		//
		//		boolean minus = s.indexOf("-")!=-1?true:false;
		//		try {
		//			if(split.length==1) {
		//				return new ComplexNumber(0, Double.parseDouble((minus?"-":"")+split[0].replace("i", "")));
		//			}
		//			split[1]=split[1].replace("i", "");
		//			return new ComplexNumber(Double.parseDouble((first?"-":"")+split[0]), Double.parseDouble((minus?"-":"")+(split[1].equals("")?"1":split[1])));
		//		}catch(Exception e) {
		//			throw new IllegalArgumentException("The given string: '"+s+"' is not parsable");
		//		}
	}
	/** Getter for real values of the ComplexNumber
	 * @return real value of the <code>ComplexNumber</code>
	 */
	public double getReal() {
		return real;
	}

	/**Getter for imaginary values of <code>ComplexNumber</code>
	 * @return imaginary value of the <code>ComplexNumber</code>
	 */
	public double getImaginary() {
		return imaginary;
	}
	/**Method that calculates magnitude
	 * @return magnitude of the <code>ComplexNumber</code> object it is being called on
	 */
	public double getMagnitude() {
		return Math.sqrt(real*real+imaginary*imaginary);
	}

	/**Method that returns the angle of the <code>ComplexNumber</code> object it is called on
	 * @return angle of the <code>ComplexNumber</code> object
	 */
	public double getAngle() {
		double retval = Math.atan2(imaginary, real);
		if(retval<0) {
			return retval+2*Math.PI; 
		}
		return retval;
	}
	/**Adds two <code>ComplexNumbers</code>
	 * @param c the <code>ComplexNumber</code> to be added to the first <code>ComplexNumber</code>
	 * @return new <code>ComplexNumber</code> that is the result
	 */
	public ComplexNumber add(ComplexNumber c) {
		real = this.real + c.real;
		imaginary = this.imaginary + c.imaginary;
		return new ComplexNumber(real, imaginary);
	}
	/**Subtracts <code>ComplexNumbers</code>
	 * @param c the <code>ComplexNumber</code> to subtract from the <code>ComplexNumber</code> the method is called upon
	 * @return <code>ComplexNumber</code> that is the result of subtraction
	 */
	public ComplexNumber sub(ComplexNumber c) {
		real = this.real - c.real;
		imaginary = this.imaginary - c.imaginary;
		return new ComplexNumber(real, imaginary);
	}
	/**Multiples the two <code>ComplexNumbers</code>
	 * @param c <code>ComplexNumber</code> that is multiplied with the first <code>ComplexNumber</code>
	 * @return the result <code>ComplexNumber</code> of multiplication
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.imaginary * c.real + c.imaginary * this.real;
		return new ComplexNumber(real, imaginary);
	}
	/**Divides the two <code>ComplexNumber</code>
	 * @param c <code>ComplexNumber</code> that is divider of the first <code>ComplexNumber</code>
	 * @return the <code>ComplexNumber</code> that is the result of division
	 */
	public ComplexNumber div(ComplexNumber c) {
		double real = (this.real*c.real+this.imaginary*c.imaginary)/(c.real*c.real+c.imaginary*c.imaginary);
		double imaginary = (this.imaginary*c.real-this.real*c.imaginary)/(c.real*c.real+c.imaginary*c.imaginary);
		return new ComplexNumber(real, imaginary);
	}

	/**Raises the <code>ComplexNumber</code> to nth power
	 * @param n the power to which raise the <code>ComplexNumber</code>
	 * @return <code>ComplexNumber</code> of the nth power
	 */
	public ComplexNumber power(int n) {
		//z^n = r^n * ( cos(nTheta) + isin(nTheta))
		return ComplexNumber.fromMagnitudeAndAngle(Math.pow(this.getMagnitude(), n),this.getAngle()*n);
	}
	/**Calculates the roots of the <code>ComplexNumber</code>
	 * @param n which root to calculate of the <code>ComplexNumber</code>
	 * @return List of <code>ComplexNumber</code> that are the solutions of rooting
	 */
	public ComplexNumber[] root(int n) {
		ComplexNumber[] retArr = new ComplexNumber[n];
		double r = Math.pow(this.getMagnitude(), 1.0/n);
		double theta = this.getAngle();
		for(int i = 0 ; i < n;i++) {
			retArr[i] = ComplexNumber.fromMagnitudeAndAngle(r,(theta + 2 * Math.PI * i )/n);
		}

		return retArr;
	}
	/**
	 *Returns the formatted form of <code>ComplexNumber</code> %d +- %d i
	 */
	public String toString() {
		if(real==0&&imaginary==0) return "0+0i";
		return (real==0?"":real+(imaginary>0?"+":"")) +  (imaginary==0?"":(imaginary+"i"));
	}

}
