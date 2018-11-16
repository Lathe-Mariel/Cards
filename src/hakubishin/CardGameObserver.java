package hakubishin;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;

import hakubishin.card_operations.CardHub;
import hakubishin.card_operations.CardUtil;

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
			if (card.toString().equals(name)) {
				cardlist.remove(card);
				break;
			}
		}
	}

	void returnAllCards() {
		CardHub player = null;
		for (Iterator<CardHub> i = players.iterator(); i.hasNext();) {
			player = i.next();
			while (player.getCardList().size() > 0) {
				sendCard(player.getCardList().get(0), player, cardsStock);
			}
		}
		for(Iterator<Card> i = cardsStock.getCardList().iterator(); i.hasNext();) {
			i.next().setClicked(false);
		}
		cardsStock.reshuffle();
	}

	public void setRuler(Ruler ruler) {
		this.ruler = ruler;
	}

	public void createPlayer( String name, boolean isShowable) {
		players.add(new CardHub(name, frame.addNewPlayer(name), isShowable));
	}
/**
 * This method will be called by Card objects when they clicked.
 * Then this call Ruler's "cardSelected(card".
 * This just conduct as a repeater between Card and Ruler.
 * @param card Card stands for the card which posed event
 */
	public void fire(Card card) {
		if (processing) {
			card.setClicked(false);
			return;
		}
		ruler.cardSelected(card);
	}

	class ProvideCards extends Thread {
		int sended = 0;
		private Card card;
		private CardHub player;

		public ProvideCards(Card card, CardHub player, boolean state) {
			this.card = card;
			this.player = player;
		}

		public void run() {
			sendCard(card, cardsStock, player);
			sended++;
			try {
				Thread.sleep(Preference.waitTime);
			} catch (Exception e) {
			}
		}
	}

/**
 *	Providing cards to the players from cardStock which is hold all cards before a game, just like dealer
 * @param number	How many cards do you want to provide
 * @param playerIndex	Index number of players which is unique
 * @param state	Card's state, wheather front or back
 * @return	Total number of sended cards
 */
	public int provideCards(int number, int playerIndex, boolean state) {
		processing = true;
		CardHub player = players.get(playerIndex);
		ProvideCards thread = null;

		for (int i = 0; i < number; i++) {
			Card card = cardsStock.next();
			if (card != null) {
				card.setUpDown(state);
				try {
					thread = new ProvideCards(card, player, state);
					if (!SwingUtilities.isEventDispatchThread()) {
						SwingUtilities.invokeAndWait(thread);
					} else {
						System.out.println("thread.run");
						thread.run();
					}
				} catch (Exception e) {
				}
			}
		}

		processing = false;
		return thread.sended;
	}

	public void sendCommand(int key) {
		switch (key) {
		case 0:
			ruler.pushButton1();
			break;
		case 1:
			ruler.pushButton2();
			break;
		case 9:
			//ruler.renew();
			Thread newGame = new Thread(ruler);
			newGame.start();
			break;
		default:
			break;
		}
	}
	public void sendNumberCommand(int number) {
		ruler.numberCommand(number);
	}

	public boolean sendCard(Card card, CardHub origin, CardHub destination) {
		boolean b = cardUtil.sendCard(card, origin, destination);
		if (origin.isShowable()) {
			frame.removeCard(origin.getSerialNumber(), card);
		}
		if (destination.isShowable()) {
			card.setContainer(frame.getCardContainer(destination.getSerialNumber()));
			frame.addCard(destination.getSerialNumber(), card);
		}
		return b;
	}
}
