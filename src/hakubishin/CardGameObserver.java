package hakubishin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

import javax.swing.SwingUtilities;

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

	Timer timer;

	public synchronized int provideCards(int number, int playerIndex, boolean state) {
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
		//			timer = new Timer(true);
		//			timer.schedule(thread, 0, 100);
		//		CardHub player = players.get(playerIndex);
		//		for (int i = 0; i < number; i++) {
		//			Card card = cardsStock.next();
		//			if (card == null)
		//				break;
		//			card.setUpDown(state);
		//			sendCard(card, cardsStock, player);
		//			frame.repaint();
		//			sended++;
		//			try {
		//				Thread.sleep(Preference.waitTime);
		//			} catch (Exception e) {
		//			}
		//		}
		processing = false;
		return thread.sended;
	}

	public void sendCommand(int key) {
		switch (key) {
		case 0:
			ruler.pushButton1();
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
