package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.*;

import hr.fer.zemris.java.gui.calc.model.*;

/**Class that is used to create Calculator GUI and connect it with model, implementing full functionality of a calculator
 * @author Fran Kristijan Jelenčić
 *
 */
public class Calculator extends JFrame {
	private CalcModelImpl model;
	private ArrayList<CalcInvertableButton> invertableButtons;
	private Stack<Double> stack;

	/**Constructor for Calculator 
	 */
	public Calculator() {
		model = new CalcModelImpl();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		invertableButtons = new ArrayList<CalcInvertableButton>(7);
		stack = new Stack<Double>();
		setSize(700,500);
		initGUI();
	}
	
	
	/**Method used for initialisation of GUI, creates and adds all buttons along with their functionality
	 * 
	 */
	private void initGUI() {

		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		//c
		cp.add(createLabel("0"),new RCPosition(1, 1));

		cp.add(createButton("clr",e->model.clear()),new RCPosition(1, 7));
		cp.add(createButton("reset",e->{
			model.clearAll();
			stack.clear();
			}),new RCPosition(2, 7));
		cp.add(createButton("push",e->stack.push(model.getValue())),new RCPosition(3, 7));
		cp.add(createButton("pop",e->{
			if(!stack.isEmpty()) {
				model.setValue(stack.pop());
			}
		}),new RCPosition(4, 7));
		
		cp.add(createCheckBox("inv"),new RCPosition(5, 7));

		cp.add(createDigitButton("7"),new RCPosition(2, 3));
		cp.add(createDigitButton("8"),new RCPosition(2, 4));
		cp.add(createDigitButton("9"),new RCPosition(2, 5));
		cp.add(createDigitButton("4"),new RCPosition(3, 3));
		cp.add(createDigitButton("5"),new RCPosition(3, 4));
		cp.add(createDigitButton("6"),new RCPosition(3, 5));
		cp.add(createDigitButton("1"),new RCPosition(4, 3));
		cp.add(createDigitButton("2"),new RCPosition(4, 4));
		cp.add(createDigitButton("3"),new RCPosition(4, 5));
		cp.add(createDigitButton("0"),new RCPosition(5, 3));
		cp.add(createButton("+/-",e->model.swapSign()),new RCPosition(5, 4));
		cp.add(createButton(".",e->model.insertDecimalPoint()),new RCPosition(5, 5));

		cp.add(createButton("=",e->{
			DoubleBinaryOperator dbo = model.getPendingBinaryOperation();
			if(dbo!=null) {
				model.setValue(dbo.applyAsDouble(model.getValue(), model.getActiveOperand()));
			}
			model.setPendingBinaryOperation(null);
			model.setActiveOperand(0);
		}),new RCPosition(1, 6));

		cp.add(createButton("/",e->{
			DoubleBinaryOperator dbo = model.getPendingBinaryOperation();
			if(dbo!=null) {
				model.setValue(dbo.applyAsDouble(model.getValue(), model.getActiveOperand()));
			}
			model.setActiveOperand(model.getValue());
			model.setPendingBinaryOperation((l,r)->r/l);})
				,new RCPosition(2, 6));
		
		cp.add(createButton("*",e->{
			DoubleBinaryOperator dbo = model.getPendingBinaryOperation();
			if(dbo!=null) {
				model.setValue(dbo.applyAsDouble(model.getValue(), model.getActiveOperand()));
			}
			model.setActiveOperand(model.getValue());
			model.setPendingBinaryOperation((l,r)->l*r);})
				,new RCPosition(3, 6));
		
		cp.add(createButton("-",e->{
			DoubleBinaryOperator dbo = model.getPendingBinaryOperation();
			if(dbo!=null) {
				model.setValue(dbo.applyAsDouble(model.getValue(), model.getActiveOperand()));
			}
			model.setActiveOperand(model.getValue());
			model.setPendingBinaryOperation((l,r)->r-l);})
				,new RCPosition(4, 6));
		
		cp.add(createButton("+",e->{
			
			DoubleBinaryOperator dbo = model.getPendingBinaryOperation();
			if(dbo!=null) {
				model.setValue(dbo.applyAsDouble(model.getValue(), model.getActiveOperand()));
			}
			model.setActiveOperand(model.getValue());
			model.setPendingBinaryOperation((l,r)->l+r);})
			,new RCPosition(5, 6));

		cp.add(createButton("1/x",e->{

			model.setValue(1/model.getValue());

		}),new RCPosition(2, 1));

		CalcInvertableButton sin = new CalcInvertableButton("sin", "arcsin",
				createAction(l->model.setValue(Math.sin(model.getValue())))
				,
				createAction(l->model.setValue(Math.asin(model.getValue()))));
		invertableButtons.add(sin);
	
		cp.add(sin,new RCPosition(2, 2));

		CalcInvertableButton log = new CalcInvertableButton("log", "10^x",
				createAction(l->model.setValue(Math.log10(model.getValue()))),
				createAction(l->model.setValue(Math.pow(10, model.getValue()))));
		invertableButtons.add(log);
		
		cp.add(log,new RCPosition(3, 1));

		CalcInvertableButton cos = new CalcInvertableButton("cos", "arccos",
				createAction(l->model.setValue(Math.cos(model.getValue()))),
						createAction(l->model.setValue(Math.acos(model.getValue()))));
		invertableButtons.add(cos);
		cp.add(cos,new RCPosition(3, 2));

		CalcInvertableButton ln = new CalcInvertableButton("ln", "e^x",
				createAction(l->model.setValue(Math.log(model.getValue()))),
						createAction(l->model.setValue(Math.pow(Math.E, model.getValue()))));
		invertableButtons.add(ln);
		cp.add(ln,new RCPosition(4, 1));

		CalcInvertableButton tan = new CalcInvertableButton("tan", "arctan",
				createAction(l->model.setValue(Math.tan(model.getValue()))),
						createAction(l->model.setValue(Math.atan(model.getValue()))));
		invertableButtons.add(tan);
		cp.add(tan,new RCPosition(4, 2));

		CalcInvertableButton xn = new CalcInvertableButton("x^n", "x^(1/n)",
				e->{
					DoubleBinaryOperator dbo = model.getPendingBinaryOperation();
					if(dbo!=null) {
						model.setValue(dbo.applyAsDouble(model.getValue(), model.getActiveOperand()));
					}
					model.setActiveOperand(model.getValue());
					model.setPendingBinaryOperation((l,r)->Math.pow(r, l));
					},
				e->{
					DoubleBinaryOperator dbo = model.getPendingBinaryOperation();
					if(dbo!=null) {
						model.setValue(dbo.applyAsDouble(model.getValue(), model.getActiveOperand()));
					}
					model.setActiveOperand(model.getValue());
					model.setPendingBinaryOperation((l,r)->Math.pow(r, 1/l));
					});
		invertableButtons.add(xn);
		cp.add(xn,new RCPosition(5, 1));

		CalcInvertableButton ctg = new CalcInvertableButton("ctx", "arcctg",
				createAction(l->model.setValue(1/Math.tan(model.getValue()))),
						createAction(l->model.setValue(1/Math.atan(model.getValue()))));
		invertableButtons.add(ctg);
		cp.add(ctg,new RCPosition(5, 2));

		//cp.setPreferredSize(preferredSize);
	}



	private ActionListener createAction(ActionListener ac) {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ac.actionPerformed(e);
				if(model.isActiveOperandSet()) {
					model.setActiveOperand(model.getActiveOperand());
				}else {
					model.clearActiveOperand();
				}
				
			}
		};

		}
	



	/**Helper method that creates a JLabel with given text
	 * @param text to initialise the JLabel 
	 * @return JLabel with text, yellow color and font 30f
	 */
	private JLabel createLabel(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		l.setHorizontalAlignment(JLabel.RIGHT);
		l.setFont(l.getFont().deriveFont(30f));
		model.addCalcValueListener(calcModel->l.setText(calcModel.toString()));
		return l;
	}

	/**Helper method used to create a button with a digit
	 * @param text the digit to set
	 * @return JButton that inserts given digit to model
	 */
	private JButton createDigitButton(String text) {
		JButton jb = new JButton(text);
//		jb.setBackground(Color.BLUE);

		jb.setOpaque(true);
		jb.setFont(jb.getFont().deriveFont(30f));
		jb.addActionListener(e->model.insertDigit(Integer.parseInt(text)));
		return jb;
	}
	/**Creates a button with specified operation
	 * @param text of the button
	 * @param operation what the button does when clicked
	 * @return JButton with specified parameters
	 */
	private JButton createButton(String text, ActionListener operation) {
		JButton jb = new JButton(text);
		//jb.setBackground(Color.CYAN);
		jb.setOpaque(true);
		jb.setFont(jb.getFont().deriveFont(15f));
		jb.addActionListener(operation);
		return jb;
	}

	/**Creates a checkbox that when checked inverts CalcInvertable buttons defined in the list invertableButtons
	 * @param text of the checkbox
	 * @return JCheckBox with specified parameters
	 */
	private JCheckBox createCheckBox(String text) {
		JCheckBox jcb = new JCheckBox(text);
		jcb.addActionListener(l->{
			for(CalcInvertableButton cib : invertableButtons) {
				cib.invert();
			}
		});
		return jcb;
	}

	/**Main method that creates and initialises Calculator
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
}
