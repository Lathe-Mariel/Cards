package hakubishin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import hakubishin.card_operations.CardHub;
import hakubishin.card_operations.Player;

public class OldMaid extends Thread implements Ruler {
	private CardGameObserver cgo;
	private ArrayList<Card> selectedPlayerCards;
	private Card selectedPullCard = null;
	private int turn = 0;
	private Timer t;

	public OldMaid() {
		selectedPlayerCards = new ArrayList<Card>();
	}

	public void renew() {
		t.cancel();
		CardHub player = null;
		for (Iterator<CardHub> i = cgo.players.iterator(); i.hasNext();) {
			player = i.next();
			while (player.getCardList().size() > 0) {
				cgo.sendCard(player.getCardList().get(0), player, cgo.cardsStock);
			}
		}
		cgo.cardsStock.reshuffle();
		start();
	}

	public void start() {
		cgo.provideCards(18, 0, true);
		cgo.provideCards(18, 1, false);
		cgo.provideCards(18, 2, false);
		try {
		}catch(Exception e) {}
		removeSameNumbers(cgo.players.get(1).getCardList());
		removeSameNumbers(cgo.players.get(2).getCardList());
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				computeProcess();
			}
		}, Preference.intervalTime, 5000);
		try {
			t.wait();
		} catch (Exception e) {
		}
		turn = 0;
		cgo.frame.setTurn(0);
	}

	public void init() {
		cgo.punishCard("Joker");
		cgo.createPlayer(54, "うさぎ～ぬ", true);
		cgo.createPlayer(54, "ハクビ神", false);
		cgo.createPlayer(54, "宅急便のネコ", false);
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
		if (turn == 0)
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
		removeSameNumbers(cgo.players.get(turn).getCardList());
		isWin();
		turn = nextPosition;
		cgo.frame.setTurn(nextPosition);
	}

	private void clear(CardHub player) {
		try {
			t.cancel();
		} catch (Exception e) {
		}
		cgo.frame.clearDialog(player.getPlayerName());
	}

	public void cardSelected(Card card) {
		if (card.getOwner().getNumber() > 1) {
			card.setClicked(false);
			return;
		}
		if (((Player) card.getOwner()).isHuman()) {
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

	public void removeSameNumbers(ArrayList<Card> cards) {
		int surveyPosition = 0;
		boolean isExist;
		do {
			Card card = cards.get(surveyPosition);
			isExist = false;
			for (int i = surveyPosition + 1; i < cards.size(); i++) {
				if (card.getNumber() == cards.get(i).getNumber()) {
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

	public void pushButton1() {
		removeSameNumbers(cgo.players.get(0).getCardList());
	}

	public void pushButton2() {

	}
}
