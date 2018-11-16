package hakubishin.card_operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JPanel;

import hakubishin.Card;

/**
 * This class stands for card game players consists of players which are controlling by computer.
 * @author akira
 *
 */
public class CardHub {
	ArrayList<Card> cards;
	/**
	 * This is identifying number of players.
	 * It must be unique.
	 */
	private int serialNumber;
	/**
	 * Player Name
	 */
	private String playerName;
	/**
	 * Wheather should this player's cards are showable or not.
	 * If object stands for cards stocker, like dealer, this must be false.
	 */
	private boolean showable;

	public CardHub() {
		cards = new ArrayList<Card>();
	}
/**
 * This constracter take an arg which is Player's Name.
 * @param name Player's Name.
 */
	public CardHub(String name) {
		this();
		this.playerName = name;
	}

	public CardHub(String name, int number, boolean showable) {
		this(name);
		this.serialNumber = number;
		this.showable = showable;
	}
/**
 * This is called by only CardUtil class.
 * Because to secure existence of cards
 * @param card	This card will be add into player's card list.
 * @return success or not
 */
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

	void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getSerialNumber() {
		return serialNumber;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setShowable(boolean b) {
		showable = b;
	}
	public boolean isShowable() {
		return showable;
	}
}
