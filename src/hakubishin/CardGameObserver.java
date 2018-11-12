package hakubishin;

import java.util.ArrayList;
import java.util.Iterator;

import hakubishin.card_operations.CardHub;
import hakubishin.card_operations.CardUtil;
import hakubishin.card_operations.Player;

public class CardGameObserver {
	Ruler ruler;
	CardHub cardsStock;
	CardPlayField frame = null;
	ArrayList<CardHub> players;
	CardUtil cardUtil;
	boolean processing = false;

	public void init() {
		cardUtil = new CardUtil();
		players = new ArrayList<CardHub>();
		try {
			frame = new CardPlayField();

			frame.setVisible(true);
			frame.setController(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cardsStock = new CardHub();
		cardUtil.createNewCards(cardsStock, 2);
		cardUtil.allFaceUp(cardsStock.getCardList());
		Card.setObserver(this);
	}

	public void punishCard(String name) {
		ArrayList<Card> cardlist = cardsStock.getCardList();
		for (Iterator<Card> i = cardlist.iterator(); i.hasNext();) {
			Card card = i.next();
			if (card.equals(name)) {
				cardlist.remove(card);
				break;
			}
		}
	}

	public void setRuler(Ruler ruler) {
		this.ruler = ruler;
	}

	public void createPlayer(int max, String name, boolean isHuman) {
		players.add(new Player(name, max, frame.addNewPlayer(name), isHuman));
	}

	public void fire(Card card) {
		if (processing) {
			card.setClicked(false);
			return;
		}
		ruler.cardSelected(card);
	}

	public int provideCards(int number, int playerIndex, boolean state) {
		int sended = 0;
		processing = true;
		CardHub player = players.get(playerIndex);
		for (int i = 0; i < number; i++) {
			Card card = cardsStock.next();
			if (card == null)
				break;
			card.setUpDown(state);
			sendCard(card, cardsStock, player);
			frame.repaint();
			sended++;
			try {
				Thread.sleep(Preference.waitTime);
			} catch (Exception e) {
			}
		}
		processing = false;
		return sended;
	}

	public void sendCommand(int key) {
		switch (key) {
		case 0:
			ruler.pushButton1();
			break;
		case 9:
			ruler.renew();
			break;
		default:
			break;
		}
	}

	public boolean sendCard(Card card, CardHub origin, CardHub destination) {
		boolean b = cardUtil.sendCard(card, origin, destination);
		if (origin instanceof Player) {
			frame.removeCard(origin.getNumber(), card);
		}
		if (destination instanceof Player) {
			frame.addCard(destination.getNumber(), card);
		}
		return b;
	}
}
