package hr.fer.zemris.lsystems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class testLSystem {

	
	@Test
	public void testGenerate() {
		LSystemBuilder ls = new LSystemBuilderImpl();
		ls.setAxiom("F");
		ls.registerProduction('F', "F+F--F+F");
		LSystem lSystem = ls.build();
		assertEquals("F", lSystem.generate(0));
		assertEquals("F+F--F+F", lSystem.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", lSystem.generate(2));
	}
	
}
