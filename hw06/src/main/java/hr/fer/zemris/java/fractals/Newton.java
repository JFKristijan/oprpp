package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**Class that is used for Newton-Raphson iteration and drawing their fractals
 * It uses the fractal viewer library package
 * @author Fran Kristijan Jelenčić
 *
 */
public class Newton {
	private static ComplexPolynomial polynomial;
	private static ComplexRootedPolynomial crp;
	
	/**Main method, allows for input of roots of complex polynomial that are used for creating Newton-Rhapson fractals
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Netwon-Raphson iteration-based fractal viewer.");
		Scanner sc = new Scanner(System.in);
		String read="";
		List<Complex> roots = new ArrayList<Complex>();
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		do {
			System.out.print("Root "+(roots.size()+1)+"> ");
			read = sc.nextLine();
			try {
				if(!read.equals("done")) {
					roots.add(Complex.parse(read));
				}
			}catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}while(!read.equals("done"));
		sc.close();
		if(roots.size()<2) {
			System.out.println("Too few roots.");
			System.exit(0);
		}

		//crp = new ComplexRootedPolynomial(Complex.ONE,Complex.ONE, Complex.ONE_NEG,Complex.IM,Complex.IM_NEG);
		crp = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[0]));
		polynomial= crp.toComplexPolynom();
		FractalViewer.show(new Producer());

	}

	/**Class used for production of data that is drawn on canvas through Newton-Rhapson iterations
	 * @author Fran Kristijan Jelenčić
	 *
	 */
	public static class Producer implements IFractalProducer{

		/**Method that notifies given {@linkplain IFractalResultObserver} after calculating value of each pixel through Newton-Rhapson iteration
		 *Please note that it may take longer for some cases when many iterations are needed to calculate the color of a pixel
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			double convergenceTreshold = 0.001;
			double rootTreshold = 0.002;
			int maxIter = 16*16*16;
			int offset = 0;
			Complex zn;
			Complex znold;
			double module;
			ComplexPolynomial derived = polynomial.derive();
			int iter;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					Complex c = new Complex(x / (width-1.0) * (reMax - reMin) + reMin,
							(height-1.0-y) / (height-1) * (imMax - imMin) + imMin);//map_to_complex_plain(x, y, xmin, xmax, ymin, ymax, remin, remax, immin, immax);
					zn = c;
					iter = 0;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;
					} while(module>convergenceTreshold && iter<maxIter);
					int index = crp.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++]=(short) (index+1);
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);

		}

	}
}
