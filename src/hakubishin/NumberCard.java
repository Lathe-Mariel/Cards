package hakubishin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NumberCard extends Card {
	int number;
	String type;
	Color color = Color.black;

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

	public NumberCard(int number, String mark) {
		super();
		this.number = number;
		x = 34;
		if (mark.equals("heart")) {
			type = "heart";
			image = markHeart;
		}
		else if (mark.equals("diamond")) {
			type = "diamond";
			image = markDiamond;
		}
		else if (mark.equals("spade")) {
			type = "spade";
			image = markSpade;
			color = Color.red;
		}
		else if (mark.equals("crover")) {
			type = "crover";
			image = markCrover;
			color = Color.red;
		}
		if (number == 1) {
			symbol = "A";
			
			x -= 3;}
		else if (number == 10) {
			x = 20;
			symbol = Integer.valueOf(number).toString();}
		else if (number == 11) {
			symbol = "J";
			x -= 3;}
		else if (number == 12) {
			symbol = "Q";
			x -= 6;}
		else if (number == 13) {
			symbol = "K";
			x -= 4;}
		else {
			symbol = Integer.valueOf(number).toString();
		}
	}

	public int getNumber() {
		return number;
	}

	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		int fontSize = 25;
		shiftX = 0;
		if(isClicked()) {
		fontSize = 40;
		shiftX = -8;
		}
		Font font = new Font("SansSerif", Font.BOLD, fontSize);
		g.setFont(font);
		g.setColor(color);
		super.paintComponent(g);
	}

	public String toString() {
		return type + number;
	}

	public boolean equals(String name) {
		if(name.equals(type + "number"))return true;
		return false;
	}
}
