package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**Implementation of a CalcModel
 * @author Fran Kristijan Jelenčić
 *
 */
public class CalcModelImpl implements CalcModel{
	private boolean editable = true;
	private boolean sign = true;
	private String digits = "";
	private double digitsValue = 0;
	private String activeOperand = "";
	private DoubleBinaryOperator pendingOperation;
	private ArrayList<CalcValueListener> listeners;
	private String frozenValue = "";

	/**Constructor for CalcModelImpl
	 * 
	 */
	public CalcModelImpl() {
		listeners = new ArrayList<CalcValueListener>();
	}
	
	
	
	/**Attaches a CalcValueListener to this CalcModelImpl 
	 *
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
		
	}

	/**Removes a CalcValueListener from this CalcModelImpl
	 *
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
		
	}

	/**Getter for current value of CalcModelImp
	 *
	 */
	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return digitsValue;
	}

	/**Sets the value attribute of this CalcModelImpl to value given in parameter, notifies listeners
	 *
	 */
	@Override
	public void setValue(double value) {
		String num = String.valueOf(value); 
		if(num.startsWith("-")) {
			sign=false;
			digits=num.substring(1);
		}else {
			digits = num;
		}

		this.digitsValue=value;
		
		editable=false;
		notifyListeners();
	}

	/**Returns whether the model is editable or not
	 *
	 */
	@Override
	public boolean isEditable() {
		return editable;
	}

	/**Clears current value
	 *
	 */
	@Override
	public void clear() {
		digits="";
		digitsValue=0;
		editable=true;
		notifyListeners();
	}

	/**Clears current value and pending operation
	 *
	 */
	@Override
	public void clearAll() {
		activeOperand = "";
		digits = "";
		pendingOperation = null;
		digitsValue=0;
		editable=true;
		notifyListeners();
	}

	/**Swaps the sign of current value
	 *@throws CalculatorInputException if the model is not editable
	 */
	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable()) throw new CalculatorInputException("Model is not editable");

		if(digits.startsWith("-")) {
			digits = digits.substring(1);
		}else {
			digits = "-" + digits;
		}
		digitsValue = -digitsValue;
		sign = !sign;
		notifyListeners();
	}

	/**Inserts a decimal point
	 * @throws CalculatorInputException if a decimal point already exists or no other digits have been entered
	 *
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable()) throw new CalculatorInputException("Model is not editable");
		if(digits.contains(".")) throw new CalculatorInputException("A decimal point already exists");
		if(digits.replace("-", "").length()==0) throw new CalculatorInputException("Length is 0");
		digits+=".";
		notifyListeners();
	}

	/**Inserts a new digit given in parameter, 
	 *@throws CalculatorInputException if the model is not ediable or there are more than 308 digits
	 *@throws IllegalArgumentException if given digit not between 1 and 9
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable()) throw new CalculatorInputException("Model is not editable");
		if(digits.replace("-", "").length()==308) throw new CalculatorInputException("Too many digits");
		if(digit<0||digit>9) throw new IllegalArgumentException("Digit given not in range 1,9");
		if(hasFrozenValue()) {
			digits="";
			frozenValue="";
		}
		digits+=String.valueOf(digit);
		digitsValue = Double.parseDouble(digits);
		notifyListeners();
	}

	/**Returns whether or not active operand is set
	 *
	 */
	@Override
	public boolean isActiveOperandSet() {
		
		return activeOperand.length()!=0;
	}

	/**Getter for active operand
	 *@throws IllegalStateException if there is not currently active operand
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(activeOperand.length()==0) throw new IllegalStateException("No currently active operand");
		return Double.parseDouble(activeOperand);
	}

	/**Setter for active operand, freezes the current value
	 *
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand =String.valueOf(activeOperand);  
		freezeValue(String.valueOf(getValue()));
		editable=true;
		
	}

	/**Clears the currently active operand
	 *
	 */
	@Override
	public void clearActiveOperand() {
		this.activeOperand="";
		editable=true;
	}

	/**Getter for current pending binary operation
	 *
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {

		return pendingOperation;
	}

	/**Setter for pending binary operation
	 *
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation=op;
		
	}
	

	@Override
	public String toString() {
//		if(digits.length()==0) {
//			return (sign?"":"-")+"0";
//		}
		String str = String.valueOf(digitsValue);
		if(str.endsWith(".0")&&!digits.contains("."))str=str.substring(0,str.length()-2);
		return str;
	}
	
	/**Method that notifies all CalcValueChanged listeners attached to this model
	 * 
	 */
	private void notifyListeners() {
		listeners.stream().forEach(cvl->cvl.valueChanged(this));
	}
	
	/**Method that freezes the current value
	 *
	 */
	@Override
	public void freezeValue(String value) {
		frozenValue = value;
		
	}
	/**Returns whether or not there is a frozen value
	 *
	 */
	@Override
	public boolean hasFrozenValue() {
		return frozenValue.length()!=0;
	}
}
