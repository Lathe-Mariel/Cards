package hakubishin;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Card extends JPanel {
	static Image haku;
	Image image;
	boolean isClicked = false;

	int x = 5, y = 70;
static {
	try {
	haku = ImageIO.read(new File("haku.jpg"));
	}catch(IOException e) {e.printStackTrace();}
}
	public Card() {
		setSize(100, 50);
		image = haku;

//		addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e) {

//		}});
	}


	public Dimension getPreferredSize() {
		return new Dimension(50,100);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
		Font font = new Font("SansSerif", Font.BOLD, 16);
		g.setFont(font);
		g.drawString("Joker", x, y);
	}
	public String toString() {
		return "Joker";
	}
}
