package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.UnsupportedLookAndFeelException;

/**Class that is used as a layout for a Calculator implementation
 * @author Fran Kristijan Jelenčić
 *
 */
public class CalcLayout implements LayoutManager2  {

	private int gap;
	private  HashMap<RCPosition, Component> components = new HashMap<RCPosition, Component>();

	public CalcLayout(int gap) {
		this.gap=gap;
	}

	public CalcLayout() {
		this(0);
	}
	




	/**Method used to add a named component, is not supported
	 *@throws UnsupportedOperationException
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**Method that removes component from layout
	 *
	 */
	@Override
	public void removeLayoutComponent(Component comp) {

		components.values().removeIf((e)->e.equals(comp));
	}

	/**Calculates the preferred size dimensions for the specifiedcontainer, given the components it contains.
	 *
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calulateSize(parent.getComponents(),parent.getInsets(), (c)->c.getPreferredSize());
	}

	/**Calculates the minimum size dimensions for the specifiedcontainer, given the components it contains.
	 *
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {

		return calulateSize(parent.getComponents(),parent.getInsets(), (c)->c.getMinimumSize());
	}

	/**Calculates the maximum size dimensions for the specifiedcontainer, given the components it contains.
	 *
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		//return null;
		return calulateSize(target.getComponents(),target.getInsets(), (c)->c.getMaximumSize());
	}

	/**Method that lays out the specified container
	 *
	 */
	@Override
	public void layoutContainer(Container parent) {



		Insets ins = parent.getInsets();		

		double w = (parent.getWidth()-ins.left-ins.right-6*gap) / (double)7;
		double h = (parent.getHeight()-ins.top-ins.bottom-4*gap) / (double)5;
		int[] remainderWidth = createUniform((parent.getWidth()-ins.left-ins.right-6*gap) % 7,true);

		Integer maxPreferredHeight = (int) h; //components.values().stream().map(m -> m.getPreferredSize().height).max(Integer::compareTo).get();

		//Integer maxPreferredWidth = components.values().stream().map(m -> m.getPreferredSize().width).max(Integer::compareTo).get();
		//maxPreferredHeight *= parent.getHeight() / preferredLayoutSize(parent).height; 

		int[] remainderHeight = createUniform((parent.getHeight()-ins.top-ins.bottom-4*gap) % 5,false);//izgleda da ne treba?

		for(var entry:components.entrySet()) {
			if(entry.getKey().equals(new RCPosition(1, 1))) {
				entry.getValue().setBounds(ins.left, ins.top, (int) (5*w+4*gap), maxPreferredHeight);
				continue;
			}

			int i = entry.getKey().getRow();
			int j = entry.getKey().getColumn();

			entry.getValue().setBounds(
					(int)(ins.left+(j-1)*w+gap*(j-1)),			//x
					(int)(ins.top+(i-1)*h+gap*(i-1)),			//y
					(int)w+remainderWidth[j-1],					//width
					maxPreferredHeight+remainderHeight[i-1]);						//height
		}
	}

	/**Method used for creating a uniform distribution
	 * @param n number used in to create distribution
	 * @param flag true if array size is 7, false if 5
	 * @return
	 */
	private int[] createUniform(int n,boolean flag) {
		int[] ret = new int[flag?7:5];
		if(n<(flag?7:5)/2){
			for(int i = 1 ; i < (flag?7:5) && n > 0 ; i+=2, n--) {
				ret[i]=1;
			}
		}else {
			for(int i = 0 ; i < (flag?7:5) && n > 0 ; i+=2, n--) {
				ret[i]=1;

			}
			for(int i = 1 ; i < (flag?7:5) && n > 0 ; i+=2, n--) {
				ret[i]=1;
			}
		}
		return ret;
	}

	/**Adds the component into the layout at the position specified in constraints
	 * @throws NullPointerException if either parameter is null
	 * @throws CalcLayoutException if the constraint object is not of type RCPosition or String or
	 * or if it cannot add the component into the layout
	 *
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null) throw new NullPointerException("Unable to add null");
		RCPosition rcp;

		if(constraints instanceof RCPosition) {
			rcp = (RCPosition) constraints;
		}else if(constraints instanceof String) {
			rcp = RCPosition.parse((String) constraints);
		}else {
			throw new CalcLayoutException("Method cannot accept given constraints");
		}
		checkPosition(rcp);
		if(components.containsKey(rcp)) {
			throw new CalcLayoutException("Tried to insert an element in an already filled slot");
		}
		components.put(rcp, comp);
	}



	/**Method not implemented
	 *
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {

		return 0;
	}

	/**Method not implemented
	 *
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {

		return 0;
	}


	/**Not implemented
	 *
	 */
	@Override
	public void invalidateLayout(Container target) {


	}

	private void checkPosition(RCPosition rcp) {
		int r = rcp.getRow();
		int s = rcp.getColumn();

		if(r<1||r>5) throw new CalcLayoutException("Position out of range");
		if(s<1||s>7) throw new CalcLayoutException("Position out of range");
		if(r==1&&(s>1&&s<6)) throw new CalcLayoutException("Position in illegal range");

	}


	private Dimension calulateSize(Component[] componenti,Insets ins,ILayoutSize size) {
		int w = 0;
		int h = 0;
		Component c = components.get(new RCPosition(1, 1));
		for(var entry : componenti) {

			int width = size.getValue(entry).width;
			int height = size.getValue(entry).height;	
			if(c != null && c.equals(entry)) {
				width = (width - 4*gap)/5;
			}
			w = width > w ? width : w;
			h = height > h ? height : h;
		}
		return new Dimension( w * 7 + 6 * gap + ins.left + ins.right , h * 5 + 4 * gap + ins.bottom + ins.top);

	}
}
