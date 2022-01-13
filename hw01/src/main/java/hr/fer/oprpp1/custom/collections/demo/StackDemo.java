package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		ObjectStack stack = new ObjectStack();
		String[] expression = args[0].split(" ");
		for(String s: expression) {
			try {
				stack.push(Integer.parseInt(s));
			}catch (NumberFormatException e) {
				if(!"+-/*".contains(s)) throw new IllegalArgumentException("Argument given was not an operator or integer");
				int second = (int) stack.pop();
				int first = (int) stack.pop();
				switch(s) {
					case "+":
						stack.push(first+second);
						break;
					case "-":
						stack.push(first-second);
						break;
					case "*":
						stack.push(first*second);
						break;
					case "/":
						try {
						stack.push(first/second);
						}catch(ArithmeticException ex) {
							System.out.println("Division by 0 is not possible\nTerminating program.");
							return;
						}
						break;
				}
			}
		}
		
		if(stack.size()!=1)System.out.println("ERROR");
		else System.out.println(stack.pop());
		

	}

}
