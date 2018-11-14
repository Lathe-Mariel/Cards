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

	public CardsLayout(int row, int column) {
		super();
		this.column = column;
		this.row = row;
		componentMap = new ArrayList<Component>();
	}

	@Override
	public void layoutContainer(Container target) {
		synchronized (target.getTreeLock()) {
			Insets i = target.getInsets();

			int nmembers = target.getComponentCount();
			if (nmembers <= 0) {
				componentMap.clear();
				return;
			}

			for(int k =0; k < nmembers; k++) {
				if(!componentMap.contains(target.getComponent(k))) {
					componentMap.add(target.getComponent(k));}
			}

			int validX = target.getWidth() - i.left - i.right;
			int validY = target.getHeight() - i.top - i.bottom;

			int rateX = validX / column;
			int rateY = validY / row;
			for (int j = 0; j < nmembers; j++) {
				Component m = target.getComponent(j);
				if (m.isVisible()) {
					Dimension d = m.getPreferredSize();
					m.setSize(d.width, d.height);

					int componentOrder = componentMap.indexOf(m);
					System.out.println(componentOrder);
					int x = (componentOrder % column) * rateX;
					int y = (componentOrder / column) * rateY;

					m.setLocation(x, y);
				}
			}
		}
	}
}
