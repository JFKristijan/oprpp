package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Objects;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw01.ComplexNumber;

public class ComplexNumberTest {
	@Test
	public void testCreation() {
		assertEquals("1.0+1.0i", new ComplexNumber(1,1).toString());
		assertEquals("1.0", ComplexNumber.fromReal(1).toString());
		assertEquals("1.0i", ComplexNumber.fromImaginary(1).toString());
		assertEquals("2.0", ComplexNumber.fromMagnitudeAndAngle(2, 0).toString());
		assertEquals("2.5+4.9i", ComplexNumber.parse("2.5+4.9i").toString());
		assertEquals("1.0i", ComplexNumber.parse("i").toString());
		assertEquals("-1.0i", ComplexNumber.parse("-i").toString());
		assertEquals("2.5", ComplexNumber.parse("2.5").toString());
		assertEquals("-2.5-4.9i", ComplexNumber.parse("-2.5-4.9i").toString());
	}
	@Test
	public void testGetters() {
		assertEquals(1, new ComplexNumber(1,1).getReal());
		assertEquals(1, new ComplexNumber(1,1).getImaginary());
		assertEquals(Math.sqrt(8), new ComplexNumber(2,2).getMagnitude(),0.00000001);
		assertEquals(Math.PI/4, new ComplexNumber(1,1).getAngle(),0.0000001);
	}
	@Test
	public void testOperations() {
		assertEquals("4.0+8.0i",
				new ComplexNumber(1,1).add(new ComplexNumber(3, 7)).toString());
		assertEquals("5.0+5.0i",
				new ComplexNumber(10,10).sub(new ComplexNumber(5, 5)).toString());
		assertEquals("8.0",
				new ComplexNumber(2,2).mul(new ComplexNumber(2, -2)).toString());
		assertEquals("1.0i",
				new ComplexNumber(2,2).div(new ComplexNumber(2, -2)).toString());
		assertEquals(-64.0,
				new ComplexNumber(2,2).power(4).getReal(),0.0000001);
		assertEquals(0,
				new ComplexNumber(2,2).power(4).getImaginary(),0.0000001);
		ComplexNumber[] list = new ComplexNumber(2,2).root(3);
		double[] reals = new double[3];
		double[] imags = new double[3];
		int i=0;
		for(ComplexNumber c : list) {
			reals[i] = c.getReal();
			imags[i++] = c.getImaginary();
		}
		assertArrayEquals(new double[] {1.36603, -1.0,-0.3660},reals,0.0001);
		assertArrayEquals(new double[] {0.3660, 1.0,-1.36603},imags,0.0001);


	}
}
