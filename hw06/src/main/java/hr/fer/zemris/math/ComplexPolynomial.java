package hr.fer.zemris.math;

/**Class that represents a Complex Polynomial
 * f(z) = Kn*z^(n)+K(n-1)*z^(n-1)...+K(1)*z^(1)+K0  
 * where K's are factors and z is a complex number
 * @author Fran Kristijan Jelenčić
 *
 */
public class ComplexPolynomial {
	private Complex[] factors;

	/**Construct for {@link ComplexPolynomial} from an array of Complex objects that are factors
	 * @param factors from which list to genereate factors from
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors=factors;
	}

	/**Returns the order of this Complex Polynomial
	 * @return the order of the polynomial
	 */
	public short order() {
		return (short) (factors.length-1);
	}

	/**Method that multiplies this Complex Polynomial with another Complex polynomial and returns a new ComplexPolynomial object
	 * @param p polynomial to multiply with
	 * @return new ComplexPolynomial that is the result of multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] result = new Complex[p.order()+order()+1];

		for(int i = 0 ; i < factors.length  ; i++) {
			for(int j = 0 ; j < p.factors.length  ; j++) {
				result[i+j] = result[i+j]!=null?
						result[i+j].add(factors[i].multiply(p.factors[j])):
							factors[i].multiply(p.factors[j]);
			}
		}

		return new ComplexPolynomial(result);
	}

	
	/**Method that calculates the derivative of this Complex Polynomial
	 * @return new Complex Polynomial that is the result of derivation
	 */
	public ComplexPolynomial derive() {
		Complex[] result = new Complex[order()];
		for(int i = 0 ; i < result.length ; i++) {
			result[i]=factors[i+1].multiply(new Complex(i+1,0));
		}
		return new ComplexPolynomial(result);
	}

	
	/**Calculates the value of this Complex Polynomial at point z
	 * @param z The point from which to calculate the value 
	 * @return new Complex number that is the value of this Complex Polynomial at point z
	 */
	public Complex apply(Complex z) {
		Complex result=null;
		for(int i = 0 ; i < factors.length ; i++) {
			result = result==null? factors[i].multiply(z.power(i)):result.add(factors[i].multiply(z.power(i)));
		}
		return result;
	}

	/**Returns the string representation of the object
	 *
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = factors.length - 1; i >= 0 ; i--) {
			if(i!=0) {
				sb.append(factors[i]+"z^"+i+"*");
			}else {
				sb.append(factors[i]);
			}
		}
		return sb.toString();
	}
}
