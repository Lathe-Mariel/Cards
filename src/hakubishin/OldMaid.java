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
	private boolean isPlayerTurn = true;
	private Timer t;

	public OldMaid() {
		selectedPlayerCards = new ArrayList<Card>();
	}

	public void start() {
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println("run");
				computeProcess();
			}
		}, 5000, 5000);
		cgo.punishCard("Joker");
		cgo.createPlayer(54, "うさぎ～ぬ", true);
		cgo.createPlayer(54, "ハクビ神", false);
		int a = cgo.provideCards(26, 0, true);
		//System.out.println(a);
		int b = cgo.provideCards(27, 1, false);
		removeSameNumbers(cgo.players.get(1).getCardList());
		//System.out.println(b);
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

	private void computeProcess() {
		for(Iterator<CardHub> i = cgo.players.iterator(); i.hasNext();) {
			CardHub player = i.next();
			if(player.getCardList().size() == 0) clear(player);
		}
		if (isPlayerTurn)
			return;
		CardHub destination = cgo.players.get(1);
		int pickUpPosition = (int) (Math.random() * 54);
		CardHub origin = cgo.players.get(0);
		ArrayList<Card> cardList = origin.getCardList();
		pickUpPosition %= cardList.size();
		Card card = cardList.get(pickUpPosition);
		card.flip();
		cgo.sendCard(card, origin, destination);
		removeSameNumbers(cgo.players.get(1).getCardList());
		isPlayerTurn = true;
	}
	
	private void clear(CardHub player) {
		t.cancel();
		cgo.frame.clearDialog(player.getPlayerName());
	}

	public void cardSelected(Card card) {
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
			if (isPlayerTurn) {
				if (selectedPullCard != null) {
					if (selectedPullCard == card) {
						card.setClicked(false);
						card.flip();
						cgo.sendCard(card, cgo.players.get(1), cgo.players.get(0));
						isPlayerTurn = false;
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
					//System.out.println("same cards exist     " + card.getNumber() + "    :    " + cards.get(i).getNumber());
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
}
