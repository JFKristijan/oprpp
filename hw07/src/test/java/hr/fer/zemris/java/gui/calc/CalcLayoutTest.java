package hr.fer.zemris.java.gui.calc;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import hr.fer.zemris.java.gui.layouts.*;


public class CalcLayoutTest {

	
	@Test
	public void testInsert() {
		CalcLayout c = new CalcLayout(3);
		JPanel p = new JPanel(c);
		p.add(new JLabel("x"), "1,1");
		p.add(new JLabel("y"), "2,3");
		p.add(new JLabel("z"), "2,7");
		p.add(new JLabel("w"), "4,2");
		p.add(new JLabel("a"), "4,5");
		p.add(new JLabel("b"), "4,7");
	}
	
	@Test
	public void testLayout() {
		CalcLayout c = new CalcLayout(2);
		JPanel p = new JPanel(c);
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
		//System.out.println(dim.width+","+dim.height);
	}
	@Test
	public void testLayout2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
		//System.out.println(dim.width+","+dim.height);
	}
	
    @Test
    public void TestRowLessThan1() {
        CalcLayout l = new CalcLayout();
        assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel("label"), "0,2"));
    }

    @Test
    public void TestRowGreaterThan5() {
        CalcLayout l = new CalcLayout();
        assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel("label"), "6,2"));
    }

    @Test
    public void TestColumnLessThan1() {
        CalcLayout l = new CalcLayout();
        assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel("label"), "1,0"));
    }

    @Test
    public void TestColumnGreaterThan7() {
        CalcLayout l = new CalcLayout();
        assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel("label"), "1,8"));
    }

    @Test
    public void TestColumnBetween1And6WhenRow1() {
        CalcLayout l = new CalcLayout();
        assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel("label"), "1,2"));
        assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel("label"), "1,3"));
        assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel("label"), "1,4"));
        assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel("label"), "1,5"));
    }

    @Test
    public void TestRow1Column1WhenItAlreadyExists() {
        CalcLayout l = new CalcLayout();
        l.addLayoutComponent(new JLabel("label"), "1,1");
        assertThrows(CalcLayoutException.class, () -> l.addLayoutComponent(new JLabel("label2"), "1,1"));

    }


	
	
}
