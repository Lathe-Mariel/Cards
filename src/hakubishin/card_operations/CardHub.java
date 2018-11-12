package hakubishin.card_operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JPanel;

import hakubishin.Card;

public class CardHub {
	ArrayList<Card> cards;
	private int maxCardNumber;
	private JPanel field;
	private int serialNumber;
	private String playerName;

	public CardHub() {
		cards = new ArrayList<Card>();
		maxCardNumber = 54;
	}

	public CardHub(String name, int max) {
		this();
		this.maxCardNumber = max;
		this.playerName = name;
	}

	boolean add(Card card) {
		cards.add(card);
		return true;
	}

	boolean remove(Card card) {
		if (cards.size() > 0 && cards.contains(card)) {
			cards.remove(card);
			return true;
		}
		return false;
	}

	public Card next() {
		if (cards.size() == 0)
			return null;
		return cards.get(0);
	}

	public ArrayList<Card> getCardList() {
		return cards;
	}

	public void reshuffle() {
		Collections.shuffle(cards);
	}

	public void showCards(JPanel panel) {
		for (Iterator<Card> i = cards.iterator(); i.hasNext();) {
			panel.add(i.next());
		}
	}
	
	void setNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getNumber() {
		return serialNumber;
	}
	
	public String getPlayerName() {
		return playerName;
	}
}
