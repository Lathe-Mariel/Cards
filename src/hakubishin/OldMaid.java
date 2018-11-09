package hakubishin;

import java.util.ArrayList;

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

	public void cardSelected(Card card) {
		if (card.isClicked) {
			selectedCards.add(card);
		}else if(!card.isClicked) {
			selectedCards.remove(card);
		}
	}
}
