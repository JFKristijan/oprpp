package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JComponent;

/**Class used for drawing a bar chart
 * @author Fran Kristijan Jelenčić
 *
 */
public class BarChartComponent extends JComponent {
	private BarChart bc;

	private static final long serialVersionUID = 1L;
	public BarChartComponent(BarChart bc) {
		this.bc=bc;
	}
	

	

	/**Paints a bar chart from BarChart model given in constructor, chart is 90% width and 85% height of container 
	 *
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; 
		
		Dimension dim = getSize();
		Insets ins = getInsets();
		Font font = getFont().deriveFont(15f);
		g2.setFont(font);
		FontMetrics fm = g2.getFontMetrics();
		int initalLeftOffset = 20;
		int dashLenght = 10;
		//Dimension canvasDim = new Dimension((int)(dim.width*0.9),(int)(dim.height*0.9));
		


		
		//lijeva strana 20px FONT 20 px Brojevi 5 px OS
		int widthX = fm.stringWidth(bc.getOpisX());
		
		int widthY = fm.stringWidth(bc.getOpisY());
		
		
		
		
		

		AffineTransform original = g2.getTransform();
		g2.rotate(-Math.PI/2,initalLeftOffset,(dim.height/2+widthY/2));
		
		g2.drawString(bc.getOpisY(),initalLeftOffset,(dim.height/2+widthY/2));
		g2.setTransform(original);
		int stringHeight = fm.getHeight();
		int offsetNumber = 10;
		int numberWidth = fm.stringWidth(String.valueOf(bc.getyMax()));
		int numberOfValues = bc.getValues().size();
		int offsetLeft = initalLeftOffset+stringHeight+offsetNumber+numberWidth;
		//unutarnji dio grafa (nadam se)
		Dimension canvas = new Dimension((int)(0.95*dim.width),(int)(0.8*dim.height));
		//5% from top 15% from bottom
		Line2D yAxis = new Line2D.Double(offsetLeft, 0.85*dim.height+5, offsetLeft, 0.05*dim.height);

		
		//TODO xAxis!
		int x = bc.getyMax();
		int y = bc.getStep();
		double heightOfOne = (((canvas.height-5)/(x/(double)y)));
		int numberOfDashes = (int)(((double)x/y)+0.5);
		
		for(int i = 0 ; i <= numberOfDashes ; i++) {
			Line2D dash = new Line2D.Double(offsetLeft-dashLenght/2, 0.85*dim.height - i * heightOfOne,
					offsetLeft, 0.85*dim.height - i * heightOfOne);
			g2.draw(dash);

			g2.drawString(String.valueOf(i*bc.getStep()), offsetLeft - offsetNumber-fm.stringWidth(String.valueOf(i*bc.getStep())),
					(int) ((0.85*dim.height - i * heightOfOne)+(fm.getHeight()/(double)4)));
		}
		g2.fill(createArrow(offsetLeft,(int) (0.05*dim.height)-10,-Math.PI/2));
		
		//xAxis
		Line2D xAxis = new Line2D.Double(offsetLeft, 0.85*dim.height, dim.width*0.95, 0.85*dim.height);
		
		int numColumns = bc.getValues().size();
		double columnWidth = ((xAxis.getX2()-xAxis.getX1()) / (double) numColumns)-2;
		
		List<XYValue> values = bc.getValues();//sortiraj po X
		
		Rectangle rect = new Rectangle(10, 10);
		for(int i = 0 ; i < numColumns ; i++) {
			double hw =( heightOfOne/bc.getStep() * values.get(i).getY());
			g2.setColor(Color.ORANGE);
			g2.fillRect((int)(offsetLeft+(columnWidth+2)*i),(int) (0.85*dim.height-hw), (int)columnWidth,(int)(hw+0.5));
//			if(i<numColumns-1) {
//				Stroke stroke = new BasicStroke(4f);
//				Stroke oldStroke = g2.getStroke();
//				g2.setStroke(stroke);
//				g2.drawLine((int)(offsetLeft+columnWidth/2+(columnWidth+2)*i), (int)(0.85*dim.height-hw), (int)(offsetLeft+columnWidth/2+(columnWidth+2)*(i+1)), (int)( 0.85*dim.height-(heightOfOne/bc.getStep() * values.get(i+1).getY())));
//				g2.setStroke(oldStroke);
//			}
			g2.setColor(Color.BLACK);
			g2.drawString(String.valueOf(values.get(i).getX()),
					(int)(columnWidth/(double)2+offsetLeft+(columnWidth+2)*i-fm.stringWidth(String.valueOf(values.get(i).getX()))/(double)2),
					(int)(0.85*dim.height+30));
		}
		
		g2.fill(createArrow((int)(numColumns*2+offsetLeft+(columnWidth+2)*numColumns)+4,(int) (0.85*dim.height),0));
		g2.drawString(bc.getOpisX(),(int) (dim.width/2-widthX/2), (int) (0.85*dim.height+50));
		g2.draw(yAxis);
		g2.draw(xAxis);
	}
	
	
	private Shape createArrow(int x, int y, double rotation) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(rotation,x,y);

		Shape s = transform.createTransformedShape(new Polygon(new int[] {x,x-10,x-10},new int[] {y,y-5,y+5},3));
				return s;
	}
}
