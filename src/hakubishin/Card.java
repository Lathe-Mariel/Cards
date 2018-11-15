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

import hakubishin.card_operations.CardHub;

public class Card extends JPanel {

	private static CardGameObserver observer;
	static Image haku1, haku2, back;
	String symbol;
	Image image;
	private boolean isFront;
	private boolean isClicked = false;
	int x = 30, y = 70, width = 50, height = 80;
	private CardHub owner;
	int shiftX = 0;

	static {
		try {
			haku1 = ImageIO.read(new File("haku.jpg"));
			back = ImageIO.read(new File("back.jpg"));
			haku2 = ImageIO.read(new File("haku2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOwner(CardHub owner) {
		this.owner = owner;
	}

	public CardHub getOwner() {
		return owner;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean b) {
		isClicked = b;
		if(this instanceof NumberCard)return;
		if(b) {
			image = haku2;
		}else {
			image = haku1;
		}
	}
	public boolean isFront() {
		return isFront;
	}

	public void setFront(boolean isFront) {
		this.isFront = isFront;
	}
	public void flip() {
		isFront = !isFront;
		setClicked(false);
		repaint();
	}

	public void setUpDown(boolean state) {
		isFront = state;
	}

	public static CardGameObserver getObserver() {
		return observer;
	}

	public static void setObserver(CardGameObserver observer) {
		Card.observer = observer;
	}

	public Card() {
		setSize(50, 80);
		image = haku1;
		symbol = "Joker";
		isFront = false;
		x = 20;

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				isClicked = !isClicked;
				setClicked(isClicked);
				fireClick();
				repaint();
			}
		});
	}
	public boolean isVisible() {
		return true;
	}

	private void fireClick() {
		observer.fire(this);
	}

	public int getNumber() {
		return 0;
	}

	public Dimension getPreferredSize() {
		return new Dimension(50, 80);
	}

	public Dimension getMaximumSize() {
		return new Dimension(50, 80);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isFront) {
			g.drawImage(image, 0, 0, this);
			g.drawString(symbol, x + shiftX, y);
		} else {
			g.drawImage(back, 0, 0, this);
			//g.drawString(symbol,  x + shiftX,  y);
		}
		if (isClicked) {
			g.setColor(Color.GRAY);
			g.draw3DRect(0, 0, width - 1, height - 1, false);
		}
	}

	public String toString() {
		return symbol;
	}

	public boolean equals(String name) {
		if(name.equals("Joker"))return true;
		return false;
	}
}
