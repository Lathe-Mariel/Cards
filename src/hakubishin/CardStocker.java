package hakubishin;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

public class CardStocker extends FlowLayout {

	private int column;

	public CardStocker(int column) {
		super();
		this.column = column;
	}

	@Override
	public void layoutContainer(Container target) {
		synchronized (target.getTreeLock()) {
			Insets i = target.getInsets();

			int nmembers = target.getComponentCount();
			if (nmembers <= 0) {
				return;
			}

			int validX = target.getWidth() - i.left - i.right;

			int rateX = validX / column -2;
			int rateY = 1 + 
					target.getComponent(0).getPreferredSize().height;
			for (int j = 0; j < nmembers; j++) {
				Component m = target.getComponent(j);
				if (m.isVisible()) {
					Dimension d = m.getPreferredSize();
					m.setSize(d.width, d.height);
					int x = (j % column) * rateX;
					int y = (j / column) * rateY;
					m.setLocation(x, y);
				}
			}
		}
	}
}
