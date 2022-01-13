package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import  hr.fer.zemris.java.gui.layouts.*;


/**Class used as a demo for BarChart, draws a bar chart from text file whose path is given as an argument when run, if no path is given
 * creates a bar chart with demo values
 * @author Fran Kristijan Jelenčić
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public BarChartDemo(List<XYValue> list,String xAxis,String yAxis,int yMin,int yMax,int step,String document) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		BarChartComponent bcc = new BarChartComponent(new BarChart(list,
				xAxis,
				yAxis,
				yMin, // y-os kreće od 0
				yMax, // y-os ide do 22
				step
				));
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(bcc,BorderLayout.CENTER);
		if(document.length()==0)document="No file";
		JLabel name = new JLabel(document,SwingConstants.CENTER);
		cp.add(name,BorderLayout.PAGE_START);
	}
	


	/**Main method used for getting text file and reading it if given, otherwise uses default values to create a barchart and 
	 * display it to the users
	 * @param args
	 */
	public static void main(String[] args) {
		BarChartDemo bcd =null;
		String[] read = new String[6];
		read[0]="Number of people in the car";
		read[1]="Frequency";
		read[2]="1,8 2,20 3,22 4,10 5,4";
		read[3]="0";
		read[4]="22";
		read[5]="2";
		if(args.length>0) {
			try {
				FileInputStream is = new FileInputStream(args[0]);
				Scanner sc = new Scanner(is);
				for(int i = 0 ; i < 6 ; i++) {
					read[i] = sc.nextLine();
				}

			}catch(Exception e) {}
		}
		List<XYValue> list = parseValues(read[2]);
		int yMin = Integer.parseInt(read[3]);
		int yMax = Integer.parseInt(read[4]);
		int step = Integer.parseInt(read[5]);
		SwingUtilities.invokeLater(()->{
			new BarChartDemo(list,read[0],read[1],yMin,yMax,step,args[0]).setVisible(true);
		});
	}
	/**Method that parses XYValues from string
	 * @param str to parse from
	 * @return List of XYValues parsed from given string
	 */
	private static List<XYValue> parseValues(String str) {
		String[] val = str.split(" ");
		ArrayList<XYValue> arr = new ArrayList<XYValue>();
		for(String s : val ) {
			if(!s.contains(",")) {
				throw new IllegalArgumentException("Unable to parse");
			}
			String[] splitted = s.replace(" ","").split(",");
			if(splitted.length!=2)
				throw new IllegalArgumentException("Insufficient number of parameters");
			arr.add(new XYValue(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])));
		}
		return arr;
	}


}
