package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;
import java.util.stream.Stream;



import hr.fer.oprpp1.custom.collections.Dictionary;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;


/**Class that represents an implementation of LSystemBuilder interface, used for building an LSystem
 * @author Fran Kristijan Jelenčić
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder{
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	double angle = 0;
	private String axiom = "";
	private Dictionary<Character,Command> dictCommand = new Dictionary<Character, Command>();
	private Dictionary<Character,String> dictProduction = new Dictionary<Character, String>();
	
	
	/**Builds an LSystem object using the given parameters
	 *@return LSystem object that with the given parameters
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**Parses the parameters from the given "text" (String array), parameters that do not exists in array are left to default values
	 *@return LSystemBuilder this
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		
		for(int i = 0 ; i < arg0.length ; i++) {
			String s = arg0[i];
			
			if(s.length()==0)continue;
			
			String[] args = s.replace("/", " / ").replaceAll("\\s+", " ").split(" ");
			try {
				switch(args[0]) {
				case "origin":
					setOrigin(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
					break;
					
				case "angle":
					setAngle(Double.parseDouble(args[1]));
					break;
					
				case "unitLength":
					setUnitLength(Double.parseDouble(args[1]));
					break;
					
				case "unitLengthDegreeScaler":
					if(args.length==4) {
						setUnitLengthDegreeScaler(Double.parseDouble(args[1])/Double.parseDouble(args[3]));
					}else {
						setUnitLength(Double.parseDouble(args[1]));
					}
					break;
					
				case "command":
					registerCommand(args[1].charAt(0), s.substring("command $".length()));
					break;
					
				case "axiom":
					setAxiom(s.substring("axiom ".length()));
					break;
					
				case "production":
					registerProduction(args[1].charAt(0),args[2] );
					break;
					
				default:
					throw new RuntimeException("Error parsing");
				}
			}catch(Exception e) {
				System.out.println("An error has occurred during configuring from text.\nPlease check for errors"+e);
			}
		}
		
		return this;
	}

	/** Registers the given command into map
	 * @param arg0 the key of the command
	 * @param arg1 String that will be parsed into the respective command
	 *@return LSystemBuilder this
	 *
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String[] splitted = arg1.trim().split(" ");
		double arg2=0.0;
		String color="";
		Command com;
		
		if(splitted.length==2) {
			try {
				arg2=Double.parseDouble(splitted[1]);
			}catch(NumberFormatException e) {
				color=splitted[1];
				//System.out.println("ERR"+e);
			}
		}
		
		switch(splitted[0]) {
			case "draw" ->	com = new DrawCommand(arg2);

			case "skip" ->com = new SkipCommand(arg2);

			case "scale" -> com = new ScaleCommand(arg2);

			case "rotate" ->com = new RotateCommand(arg2);

			case "push" ->com = new PushCommand();
			
			case "pop" ->com = new PopCommand();

			case "color" ->	com = new ColorCommand(Color.decode("0x"+color));
			default -> throw new IllegalArgumentException("Unparseable string given");
			}
		
		dictCommand.put(arg0, com);
		return this;
	}

	/**Registers the given production into object
	 * @param arg0 key for the production
	 * @param arg1 String that is the production
	 *@return LSystemBuilder this
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		dictProduction.put(arg0, arg1);
		return this;
	}

	/**Setter for angle
	 *@return LSystemBuilder this
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle=arg0;
		return this;
	}

	/**Setter for axiom
	 *@return LSystemBuilder this
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		axiom=arg0;
		return this;
	}

	/**Setter for origin
	 *@return LSystemBuilder this
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0,arg1);
		return this;
	}

	/**Setter for unit length
	 *@return LSystemBuilder this
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength=arg0;
		return this;
	}

	/**Setter for unit length degree scaler
	 *@return LSystemBuilder this
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler=arg0;
		return this;
	}
	
	/**Implementation of a LSystem
	 * @author Fran Kristijan Jelenčić
	 *
	 */
	private class LSystemImpl implements LSystem{

		/**Draws the given fractal with the parameters given by {@link LSystemBuilder}
		 *
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context ctx = new Context();
			TurtleState ts = new TurtleState(origin.copy(),new Vector2D(1,0).rotated(Math.toRadians(angle)),
					Color.BLACK,
					unitLength*Math.pow(unitLengthDegreeScaler, arg0));
			String commands = generate(arg0);
			ctx.pushState(ts);
			for(int i = 0; i < commands.length() ; i++) {
				Command cmd = dictCommand.get(commands.charAt(i));
				if(cmd==null)continue;
				cmd.execute(ctx, arg1);
			}
			//commands.chars().forEach(c->dictCommand.get((char)c).execute(ctx, arg1));
			
		}

		/**
		 *Generates a list of commands from the given argument and axiom
		 *@param arg0 how many iterations to do
		 */
		@Override
		public String generate(int arg0) {
			String s = axiom;
			StringBuilder sb = new StringBuilder();
			for(int i = 0 ; i < arg0 ; i++) {
				for(int j = 0 ; j < s.length(); j++) {
					String str = dictProduction.get(s.charAt(j));
					sb.append(str==null?s.substring(j, j+1):str);
				}

				s=sb.toString();
				sb.setLength(0);
			}
			
			return s;
		}
		
	}


}
