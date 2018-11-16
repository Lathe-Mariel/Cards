package hakubishin;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingUtilities;

import hakubishin.card_operations.CardHub;

public class MemoryCards implements Ruler {
	private ArrayList<Card> processCards;
	private int relationNumber;
	private int turn;
	private CardHub field;
	private CardGameObserver cgo;
	private boolean process;
	private int playerNumber;
	ArrayList<Card> cardMapping;//memorizing cards which already opened for computer players

	public MemoryCards() {
		processCards = new ArrayList<Card>();
		relationNumber = 2;
		field = new CardHub();
		playerNumber = 2;
		cardMapping = new ArrayList<Card>();
	}

	public void setObserver(CardGameObserver cgo) {
		this.cgo = cgo;
	}

	@Override
	public void run() {
		for(int i= 0; i < cgo.players.size(); i++)
			cgo.frame.setTurn(i, 0);
		cardMapping.clear();
		cgo.returnAllCards();
		start();
	}

	@Override
	public void cardSelected(Card card) {
		// TODO 自動生
		if (card.getOwner() == cgo.players.get(0)) {
			if (turn == 1) {
				pickUpCard(card);
			} else {
				card.setClicked(false);
			}
		}
	}

	private synchronized void pickUpCard(Card card) {
		if (process == true) {
			card.setClicked(false);
			return;
		}
		process = true;
		if (card.isFront() == true) {
			process = false;
			return;
		}

		flipCard(card);
		processCards.add(card);
		try {
			new Thread(new Runnable() {
				public void run() {
					try {
						checkSameCards();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		} catch (Exception f) {
			f.printStackTrace();
		}
	}

	private void flipCard(Card card) {
		if (!cardMapping.contains(card)) {
			cardMapping.add(card);
		}
		card.flip();
	}

	private boolean checkSameCards() {
		if (processCards.size() != 2) {
			process = false;
			return false;
		} else {
			try {
				Thread.sleep(900);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Card card1 = processCards.get(0);
			Card card2 = processCards.get(1);
			System.out.println("card1: " + card1.toString() + "    :    card2: " + card2.toString());
			if (card1.getNumber() == card2.getNumber()) {
				cgo.sendCard(card1, card1.getOwner(), cgo.players.get(turn));
				cgo.sendCard(card2, card2.getOwner(), cgo.players.get(turn));
				processCards.clear();
				if (cardMapping.contains(card1))
					cardMapping.remove(card1);
				if (cardMapping.contains(card2))
					cardMapping.remove(card2);
				turn--;
			} else {
				closeCardFlip();
			}
			process = false;
			proceedTurn();
			return true;
		}
	}

	/**
	 * All selected cards(processCards) will be backed
	 */
	private void closeCardFlip() {
		for (Iterator<Card> i = processCards.iterator(); i.hasNext();) {
			i.next().flip();
		}
		processCards.clear();
	}

	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					try {
						cgo.frame.createSmallField(playerNumber);
						cgo.frame.createCenterField(playerNumber + 1);//this number must consists of field(it means players + 1)

						//cgo.createPlayer( "宅急便のネコ", true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception f) {
			f.printStackTrace();
		}
		cgo.createPlayer("", true);//field
		cgo.createPlayer("うさぎ～ぬ", true);//user1
		cgo.createPlayer("ハクビ神", true);//com1

		start();
	}

	private void proceedTurn() {
		turn++;
		if (turn == playerNumber + 1) {
			turn -= playerNumber;
		}
		cgo.frame.setTurn(turn, cgo.players.get(turn).getCardList().size());
		if(cgo.players.get(0).getCardList().size() == 0) {
			tally();return;
		}
		//System.out.println(turn + " 's turn");

		if (turn != 1) {
			new ComputerPlayer().start();
		}
	}
	/**
	 * confirm the result, players vs computers
	 */
	private void tally() {
		
	}

	@Override
	public void start() {
		// TODO 自動生成されたメソッド・スタブ
		process = true;
		cgo.provideCards(54, 0, false);

		turn = 1;
		cgo.frame.setTurn(1);
		process = false;
	}

	@Override
	public void pushButton1() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void pushButton2() {
		// TODO 自動生成されたメソッド・スタブ

	}

	class ComputerPlayer extends Thread {

		public ComputerPlayer() {
			this(0);
		}

		public ComputerPlayer(int howStrong) {
			super();
		}

		public void run() {
			ArrayList<Card> samecards = isThereSameCards();
			if (samecards.isEmpty()) {
				int pickUpCardNumber1 = (int) (Math.random() * cgo.players.get(0).getCardList().size());
				int pickUpCardNumber2 = pickUpCardNumber1;
				while (pickUpCardNumber1 == pickUpCardNumber2) {
					pickUpCardNumber2 = (int) (Math.random() * cgo.players.get(0).getCardList().size());
				}
				Card card1 = cgo.players.get(0).getCardList().get(pickUpCardNumber1);
				Card card2 = cgo.players.get(0).getCardList().get(pickUpCardNumber2);
				System.out.println("pick card1");
				pickUpCard(card1);
				try {
					sleep(Preference.COMPUTER_PICKUP_INTERVAL);
				} catch (Exception e) {
					System.out.println("ComputerPlayer - run() 1");
					e.printStackTrace();
				}
				pickUpCard(card2);
			} else {
				System.out.println("hit");
				Card card1 = samecards.get(0);
				Card card2 = samecards.get(1);
				pickUpCard(card1);
				try {
					sleep((int)(Preference.COMPUTER_PICKUP_INTERVAL * 1.5));
				} catch (Exception e) {
					System.out.println("ComputerPlayer - run() 2");
					e.printStackTrace();
				}
				pickUpCard(card2);
			}
		}

		private ArrayList<Card> isThereSameCards() {
			ArrayList<Card> tempCards = new ArrayList<Card>();
			for (int i = 0; i < cardMapping.size(); i++) {
				Card card1 = cardMapping.get(i);
				for (int j = i + 1; j < cardMapping.size(); j++) {
					Card card2 = cardMapping.get(j);
					if (card1.getNumber() == card2.getNumber()) {
						tempCards.add(card1);
						tempCards.add(card2);
						return tempCards;
					}
				}
			}
			return tempCards;
		}
	}

}
