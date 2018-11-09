package hakubishin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Card extends JPanel {
	static CardGameObserver observer;
	static Image haku;
	String symbol;
	Image image;
	boolean isClicked = false;
	int x = 5, y = 70, width = 50, height = 74;
static {
	try {
	haku = ImageIO.read(new File("haku.jpg"));
	}catch(IOException e) {e.printStackTrace();}
}
	public Card() {
		setSize(100, 50);
		image = haku;
		symbol = "Joker";

		addMouseListener(new MouseAdapter() {
	          public void mousePressed(MouseEvent me) {
	            isClicked = !isClicked;
	            fireClick();
	            repaint();
	          }
	        });
	}
	
	private void fireClick() {
		observer.fire(this);
	}


	public Dimension getPreferredSize() {
		return new Dimension(50,80);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
		g.drawString(symbol, x, y);
		if(isClicked) {
			g.setColor(Color.BLACK);
			g.draw3DRect(0, 0, width-1, height-1, true);
		}
	}
	public String toString() {
		return symbol;
	}
}
