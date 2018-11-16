package hakubishin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import hakubishin.card_operations.CardHub;

public class OldMaid extends Thread implements Ruler {
	private CardGameObserver cgo;
	private ArrayList<Card> selectedPlayerCards;
	private Card selectedPullCard = null;
	private int turn = 0;
	private Timer t;

	public OldMaid() {
		selectedPlayerCards = new ArrayList<Card>();
	}

	public void run() {
		t.cancel();
		cgo.returnAllCards();
		start();
	}

	public void start() {
		cgo.provideCards(18, 0, true);
		cgo.provideCards(18, 1, false);
		cgo.provideCards(18, 2, false);
		turn = 3;
		autoRemoveSameNumbers(cgo.players.get(1).getCardList());
		autoRemoveSameNumbers(cgo.players.get(2).getCardList());
		turn = 0;
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				computeProcess();
			}
		}, 5000, Preference.intervalTime);
		try {
			t.wait();
		} catch (Exception e) {
		}
		cgo.frame.setTurn(0);
	}

	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					try {
						cgo.frame.createSmallField(3);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception f) {
			f.printStackTrace();
		}
		cgo.frame.createSmallField(3);
		cgo.punishCard("Joker");
		cgo.createPlayer("うさぎ～ぬ", true);
		cgo.createPlayer("ハクビ神", true);
		cgo.createPlayer("宅急便のネコ", true);

		start();
	}

	public void setObserver(CardGameObserver cgo) {
		this.cgo = cgo;
	}

	boolean checkSameCards() {
		if (selectedPlayerCards.size() > 2) {
			for (Iterator<Card> i = selectedPlayerCards.iterator(); i.hasNext();) {
				Card card = i.next();
				card.setClicked(false);
				card.repaint();
			}
			selectedPlayerCards.clear();
			return false;
		}
		if (selectedPlayerCards.size() < 2)
			return false;
		Card card1, card2;
		card1 = selectedPlayerCards.get(0);
		card2 = selectedPlayerCards.get(1);
		System.out.println("card1: " + card1.toString() + "    :    card2: " + card2.toString());
		if (selectedPlayerCards.size() != 2)
			return false;
		if (card1.getNumber() == card2.getNumber()) {
			removeCard(card1);
			removeCard(card2);
			return true;
		}
		return false;
	}

	private void isWin() {
		for (Iterator<CardHub> i = cgo.players.iterator(); i.hasNext();) {
			CardHub player = i.next();
			if (player.getCardList().size() == 0)
				clear(player);
		}
	}

	private void computeProcess() {
		if (turn == 0 || selectedPlayerCards.size() != 0)
			return;
		int nextPosition = turn == 2 ? 0 : turn + 1;
		CardHub destination = cgo.players.get(turn);
		int pickUpPosition = (int) (Math.random() * 54);
		CardHub origin = cgo.players.get(nextPosition);
		ArrayList<Card> cardList = origin.getCardList();
		pickUpPosition %= cardList.size();
		Card card = cardList.get(pickUpPosition);
		card.setUpDown(false);
		cgo.sendCard(card, origin, destination);
		autoRemoveSameNumbers(cgo.players.get(turn).getCardList());
		isWin();
		turn = nextPosition;
		cgo.frame.setTurn(nextPosition);
	}

	private void clear(CardHub player) {
		try {
			t.cancel(); //stop timer which poses computeProcess()
		} catch (Exception e) {
		}
		cgo.frame.clearDialog(player.getPlayerName());
	}

	public void cardSelected(Card card) {
		if (card.getOwner().getSerialNumber() > 1) {
			card.setClicked(false);
			return;
		}
		if (card.getOwner().getSerialNumber() == 0) {
			if (selectedPlayerCards.size() > 2) {
				card.setClicked(false);
				card.repaint();
				return;
			}
			if (card.isClicked()) {
				selectedPlayerCards.add(card);
			} else if (!card.isClicked()) {
				selectedPlayerCards.remove(card);
			}
			checkSameCards();
		} else {
			if (turn == 0) {
				if (selectedPullCard != null) {
					if (selectedPullCard == card) {
						card.setClicked(false);
						card.flip();
						cgo.sendCard(card, cgo.players.get(1), cgo.players.get(0));
						isWin();
						turn = 1;
						cgo.frame.setTurn(turn);
						selectedPullCard = null;
					} else {
						selectedPullCard.setClicked(false);
						selectedPullCard.repaint();
						selectedPullCard = null;
					}
				}
				selectedPullCard = card;
			}
		}
	}

	public void removeCard(Card card) {
		if (selectedPlayerCards.size() != 0)
			selectedPlayerCards.clear();
		cgo.sendCard(card, card.getOwner(), cgo.cardsStock);
		card.repaint();
	}

	public void autoRemoveSameNumbers(ArrayList<Card> cards) {
		int surveyPosition = 0;
		boolean isExist;
		do {
			Card card = cards.get(surveyPosition);
			isExist = false;
			for (int i = surveyPosition + 1; i < cards.size(); i++) {
				if (card.getNumber() == cards.get(i).getNumber()) {
					Card card1 = cards.get(i);
					card.setUpDown(true);
					card1.setUpDown(true);
					try {
						if (turn != 0)
							Thread.sleep(Preference.SLEEP_AT_DISSAPEAR);

					} catch (Exception e) {
						e.printStackTrace();
					}
					card.setClicked(false);
					cards.get(i).setClicked(false);
					removeCard(cards.get(i));
					removeCard(card);
					isExist = true;
					break;
				}
			}
			if (isExist)
				continue;
			surveyPosition++;
		} while (surveyPosition < cards.size());
	}

	@Override
	public void pushButton1() {
		autoRemoveSameNumbers(cgo.players.get(0).getCardList());
	}

	@Override
	public void pushButton2() {

	}

	@Override
	public void numberCommand(int number) {
	}
}
