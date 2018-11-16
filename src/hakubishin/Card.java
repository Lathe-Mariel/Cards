package hakubishin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	int x = 30, y = 70, width = Preference.CARD_WIDTH, height = Preference.CARD_HEIGHT;
	private CardHub owner;
	int shiftX = 0;
	JPanel container;
	int number;
	String type;
	Color color = Color.black;
	int drawFontSize;
	int typeNumber;

	static {
		try {
			haku1 = ImageIO.read(new File("haku.jpg"));
			back = ImageIO.read(new File("back.jpg"));
			haku2 = ImageIO.read(new File("haku2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static Image markHeart, markDiamond, markSpade, markCrover;

	static {
		try {
			markHeart = ImageIO.read(new File("heart.jpg"));
			markDiamond = ImageIO.read(new File("diamond.jpg"));
			markSpade = ImageIO.read(new File("spade.jpg"));
			markCrover = ImageIO.read(new File("crover.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//-------------------------------Heart 0    Diamond 2    Spade 1    Crover 3--------(typeNumber)--------------------
	public int getNumber() {
		return number;
	}

	public void setContainer(JPanel container) {
		this.container = container;
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
		if (number == 0) {
			if(isClicked) {
				image = haku2;
			}else {
				image = haku1;
			}
			return;
		}else {
			if(isClicked) {
				drawFontSize = 40;
				y = 88;
			}else {
				drawFontSize = 25;
				y = 70;
			}
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
		drawFontSize = 16;
		this.number = 0;
		image = haku1;
		symbol = "Joker";
		isFront = false;
		x = 8;

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				isClicked = !isClicked;
				fireClick();
				setClicked(isClicked);
				container.revalidate();
				container.repaint();
				repaint();
			}
		});
	}

	public Card(int number, String mark) {
		this();
		this.number = number;
		x = 34;
		if (mark.equals("heart")) {//0
			type = "heart";
			typeNumber = 0;
			image = markHeart;
		} else if (mark.equals("diamond")) {//2
			type = "diamond";
			image = markDiamond;
		} else if (mark.equals("spade")) {//1
			type = "spade";
			image = markSpade;
			color = Color.red;
		} else if (mark.equals("crover")) {//3
			type = "crover";
			image = markCrover;
			color = Color.red;
		}
		if (number == 1) {
			symbol = "A";
			x = 31;
		} else if (number == 10) {
			x = 20;
			symbol = Integer.valueOf(number).toString();
		} else if (number == 11) {
			symbol = "J";
			x = 31;
		} else if (number == 12) {
			symbol = "Q";
			x = 28;
		} else if (number == 13) {
			symbol = "K";
			x = 30;
		} else {
			symbol = Integer.valueOf(number).toString();
		}
	}

	public boolean isVisible() {
		return true;
	}

	private void fireClick() {
		observer.fire(this);
	}

	public String toString() {
		if(number == 0)
			return "Joker";
		return type + number;
	}

	public boolean isSameColorNumber(Card card) {
		if((number + card.number) == 0) return true;	// cards are Jokers
		if((typeNumber + card.typeNumber)%2 == 0)return true;
		return false;
	}

	public Dimension getPreferredSize() {
		if (isClicked) {
			width = (int) (Preference.CARD_WIDTH * Preference.CARD_BIGGER_RATE);
			height = (int) (Preference.CARD_HEIGHT * Preference.CARD_BIGGER_RATE);
			return new Dimension(width, height);
		} else {
			width = Preference.CARD_WIDTH;
			height = Preference.CARD_HEIGHT;
			return new Dimension(width, height);
		}
	}

	public Dimension getMaximumSize() {
		return new Dimension(60, 96);
	}

	public void paintComponent(Graphics g) {
		Font font = new Font("SansSerif", Font.BOLD, drawFontSize);
		g.setFont(font);
		g.setColor(color);
		if (isFront) {
			g.drawImage(image, 0, 0, width, height, this);
			g.drawString(symbol, x, y);
		} else {
			g.drawImage(back, 0, 0, this);
		}
		if (isClicked) {
			g.setColor(Color.GRAY);
			g.draw3DRect(0, 0, width - 1, height - 1, false);
		}
	}
}
