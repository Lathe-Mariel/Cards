package hakubishin;

import java.util.ArrayList;

import hakubishin.card_operations.CardHub;
import hakubishin.card_operations.CardUtil;
import hakubishin.card_operations.Player;

public class CardGameObserver {
	Ruler ruler;
	CardHub cardsStock;
	CardPlayField frame = null;
	ArrayList<Player> players;
	CardUtil cardUtil;

	public void init() {
		cardUtil = new CardUtil();
		players = new ArrayList<Player>();
		try {
			frame = new CardPlayField();
			frame.setVisible(true);
			frame.setController(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cardsStock = new CardHub();
		cardUtil.createNewCards(cardsStock, true);
		cardUtil.allFaceUp(cardsStock.getCardList());
		Card.setObserver(this);
	}

	public void setRuler(Ruler ruler) {
		this.ruler = ruler;
	}

	public void createPlayer(int max, String name, boolean isHuman) {
		players.add(new Player(name, max, frame.getNewPlayerNumber(), isHuman));
	}

	public void fire(Card card) {
		ruler.cardSelected(card);
	}

	public int provideCards(int number, int playerIndex, boolean state) {
		int sended = 0;
		CardHub player = players.get(playerIndex);
		for (int i = 0; i < number; i++) {
			Card card = cardsStock.next();
			if (card == null)
				break;
			card.setUpDown(state);
			sendCard(card, cardsStock, player);
			//frame.addCard(playerIndex, card);
			sended++;
			try {
				Thread.sleep(Preference.waitTime);
			} catch (Exception e) {
			}
		}
		return sended;
	}

	public void sendCommand(int key) {
		switch (key) {
		case 0:
			((OldMaid) (ruler)).removeSameNumbers(players.get(0).getCardList());
			break;

		default:
			break;
		}
	}

	public boolean sendCard(Card card, CardHub origin, CardHub destination) {
		boolean b = cardUtil.sendCard(card, origin, destination);
		if(origin instanceof Player) {
			frame.removeCard(origin.getNumber(), card);

		}
		if(destination instanceof Player) {
			frame.addCard(destination.getNumber(), card);
		}
		return b;
	}
}
