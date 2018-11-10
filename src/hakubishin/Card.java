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
	static Image haku, back;
	String symbol;
	Image image;
	private boolean isFront;
	private boolean isClicked = false;
	int x = 5, y = 70, width = 50, height = 74;
	private CardHub owner;

	static {
		try {
			haku = ImageIO.read(new File("haku.jpg"));
			back = ImageIO.read(new File("back.jpg"));
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
	}

	public void flip() {
		isFront = !isFront;
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
		setSize(100, 50);
		image = haku;
		symbol = "Joker";
		isFront = false;

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

	public int getNumber() {
		return 0;
	}

	public Dimension getPreferredSize() {
		return new Dimension(50, 80);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(isFront) {
		g.drawImage(image, 0, 0, this);
		g.drawString(symbol, x, y);
		if (isClicked) {
			g.setColor(Color.BLACK);
			g.draw3DRect(0, 0, width - 1, height - 1, true);
		}
		}else {
			g.drawImage(back, 0, 0, this);
		}

	}

	public String toString() {
		return symbol;
	}
}
