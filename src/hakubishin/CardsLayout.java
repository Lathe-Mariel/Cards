package hakubishin;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.ArrayList;

public class CardsLayout extends FlowLayout {
	private ArrayList<Component> componentMap;
	private int column, row;

	public CardsLayout(int column, int row) {
		super();
		this.column = column;
		this.row = row;
		componentMap = new ArrayList<Component>();
		System.out.println("1");
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		System.out.println("2");
		super.addLayoutComponent(name, comp);
		componentMap.add(comp);
		System.out.println(comp.toString());
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		super.removeLayoutComponent(comp);
	}

	@Override
	public void layoutContainer(Container target) {
		synchronized (target.getTreeLock()) {
			Insets i = target.getInsets();
			//		      int top = i.top;
			//		      int bottom = target.getHeight() - i.bottom;
			//		      int left = i.left;
			//		      int right = target.getWidth() - i.right;
			//		      int vgap = getVgap();
			//		      int hgap = getHgap();
			//		      int wc = right - left;

			int nmembers = target.getComponentCount();
			if (nmembers <= 0) {
				return;
			}

			int rateX = target.getWidth() / column;
			int rateY = target.getHeight() / row;
			for (int j = 0; j < nmembers; j++) {
				Component m = target.getComponent(j);
				if (m.isVisible()) {
					Dimension d = m.getPreferredSize();
					m.setSize(d.width, d.height);

					int componentOrder = componentMap.indexOf(m);
					int x = (componentOrder % column) * rateX;
					int y = (componentOrder / row) * rateY;

					m.setLocation(x, y);
				}
			}
		}
	}
}
