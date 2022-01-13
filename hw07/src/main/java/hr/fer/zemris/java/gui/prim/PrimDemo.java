package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**Demo for a List of prime numbers, generates a new prime every time the button is pressed
 * @author Fran Kristijan Jelenčić
 *
 */
public class PrimDemo extends JFrame{

	private static final long serialVersionUID = 1L;

	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(20, 20);
		setSize(500, 200);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		PrimListModel pm = new PrimListModel();
		JPanel p = new JPanel(new GridLayout(1, 0));
		JList<Integer> left = new JList<>(pm);
		JList<Integer> right = new JList<>(pm);
		//left.setSelectionModel(right.getSelectionModel()); do i need  this?
		JButton add = new JButton("Sljedeci prim broj");
		add.addActionListener(l->pm.next());
		p.add(new JScrollPane(left));
		p.add(new JScrollPane(right));
		cp.add(p,BorderLayout.CENTER);
		cp.add(add,BorderLayout.PAGE_END);
	}

	
	/**Main method used for generating a GUI used in the demo
	 * @param args none
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new PrimDemo().setVisible(true);
		});
	}
}
