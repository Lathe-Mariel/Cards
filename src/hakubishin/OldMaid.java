package hakubishin;

import java.util.ArrayList;
import java.util.Iterator;

import hakubishin.card_operations.Cards;

public class OldMaid implements Ruler {
	private CardGameObserver cgo;
	private ArrayList<Card> selectedCards;

	public OldMaid() {
		selectedCards =new  ArrayList<Card>();
	}
	
	public void start() {
		cgo.createPlayer(54, "うさぎ～ぬ");
		cgo.createPlayer(54, "ハクビ神");
		cgo.provideCards(26, 0);
	}

	public void setObserver(CardGameObserver cgo) {
		this.cgo = cgo;
	}

	boolean checkSameCards() {
		if(selectedCards.size()> 2) {
			for(Iterator<Card> i = selectedCards.iterator(); i.hasNext();) {
				Card card = i.next();
				card.isClicked = false;
				card.repaint();
			}
			selectedCards.clear();
			return false;
		}
		if(selectedCards.size() < 2)return false;
		Card card1, card2;
		card1 = selectedCards.get(0);
		card2 = selectedCards.get(1);
		System.out.println("card1: " + card1.toString() + "    :    card2: " + card2.toString());
		if(selectedCards.size() != 2)return false;
		if(card1.getNumber() == card2.getNumber()) {
			selectedCards.remove(0);
			selectedCards.remove(0);
			Cards.sendCard(card1, cgo.players.get(0), cgo.cardsStock);
			Cards.sendCard(card2, cgo.players.get(0), cgo.cardsStock);
			cgo.frame.removeCard(0,  card1);
			cgo.frame.removeCard(0,  card2);
			return true;
		}
		return false;
	}
	
	public void cardSelected(Card card) {
		if(selectedCards.size()>2) {
			card.isClicked = false;
			card.repaint();
			return;
			
		}
		if (card.isClicked) {
			selectedCards.add(card);
		}else if(!card.isClicked) {
			selectedCards.remove(card);
		}
		checkSameCards();
	}
}
