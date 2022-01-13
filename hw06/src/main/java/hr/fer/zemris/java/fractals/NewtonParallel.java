package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;


/**Class that is used for Newton-Raphson iteration and drawing their fractals
 * It support paralelization through parameters given through the arguments uses the fractal viewer library package
 * @author Fran Kristijan Jelenčić
 *
 */
public class NewtonParallel {
	private static int workers=Runtime.getRuntime().availableProcessors();
	private static int brojTraka=4*workers;
	private static ComplexPolynomial polynomial;
	private static ComplexRootedPolynomial crp;
	public static void main(String[] args) {
		boolean w=false;
		boolean t=false;
		for(int i = 0 ; i < args.length ; i++) {
			
			try {
			String[] str = args[i].split("=");
			switch(str[0]) {
			case "-w":
				str= new String[2];
				str[1]=args[++i];
			case "--workers":
				if(w) {
					System.out.println("ERROR specified same param twice");
					System.exit(1);
				}
				workers=Integer.parseInt(str[1]);
				w=true;
				break;
			case "-t":
				str= new String[2];
				str[1]=args[++i];
			case "--tracks":
				if(t) {
					System.out.println("ERROR specified same param twice");
					System.exit(1);
				}
				brojTraka=Integer.parseInt(str[1]);
				t=true;
				break;
			default:
				System.out.println("Error while paring arguments.");
				System.exit(1);
			}
			}catch(Exception e) {
				System.out.println("Error parsing "+args[i]+" continuing with default value");
			}
		}
		System.out.println("Welcome to Netwon-Raphson iteration-based fractal viewer."
				+ "\nNow with multithreading support"
				);
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
		
		FractalViewer.show(new MojProducer());
		
	}
	
	
	
	
	
	
	/**Class that represents an iteration of a job calculating data through Newton-Rhapson iteration
	 * 
	 * @author Fran Kristijan Jelenčić
	 *
	 */
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		static final double convergenceTreshold = 0.001;
		static final double rootTreshold = 0.002;
		static ComplexPolynomial derived=polynomial.derive(); 
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		/**Empty constructor for PosaoIzracuna
		 * 
		 */
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}
		
		/**Implementation of data calcuation with parameters given through the constructor
		 *
		 */
		@Override
		public void run() {
			
			int iter;
			Complex zn;
			Complex znold;
			double module;
			for(int y = yMin; y <= yMax; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					Complex c = new Complex(x / (width-1.0) * (reMax - reMin) + reMin,(height-1.0-y) / (height-1) * (imMax - imMin) + imMin);//map_to_complex_plain(x, y, xmin, xmax, ymin, ymax, remin, remax, immin, immax);
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
					} while(module>convergenceTreshold && iter<m);
					int index = crp.indexOfClosestRootFor(zn, rootTreshold);
					data[y*width+x]=(short) (index+1);
				}
			}
			
		}
	}
	
	
	/**Class used for producing jobs that calculate specific parts of the canvas that is drawn through {@linkplain IFractalResultObserver}
	 * It creates N workers where N is either given through the arguments or is set to default as the number of available processors to the JVM
	 * The number of tracks is decided the same way, the default value for tracks is 4 * available processors
	 * @author Fran Kristijan Jelenčić
	 *
	 */
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			if(brojTraka>height) {
				brojTraka=height;
			}
			int brojYPoTraci = height / brojTraka;
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();
			System.out.println("Running image generation with "+workers+" threads"
					+ " and "+brojTraka+" jobs."
					);

			Thread[] radnici = new Thread[workers];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							PosaoIzracuna p = null;
							try {
								p = queue.take();
								if(p==PosaoIzracuna.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}
	}
}
