package hakubishin.card_operations;

import java.util.ArrayList;
import java.util.Collections;

import hakubishin.Card;
import hakubishin.NumberCard;

public class Cards {
	
	public static void createNewCards(CardHub creater, boolean hasJoker) {
		ArrayList<Card> cardSet = new ArrayList<Card>();;
		for(int i = 1; i < 14; i++) {
			cardSet.add(new NumberCard(i, "heart"));
			cardSet.add(new NumberCard(i, "diamond"));
			cardSet.add(new NumberCard(i, "spade"));
			cardSet.add(new NumberCard(i, "crover"));
		}
		if(hasJoker) {
			cardSet.add(new Card());
			cardSet.add(new Card());
		}
		Collections.shuffle(cardSet);
		creater.cards.clear();
		creater.cards.addAll(cardSet);
	}
	
	public static boolean sendCard(Card card, CardHub origin, CardHub destination) {
			boolean isSuccess = origin.remove(card);
			destination.add(card);
			return isSuccess;
		//System.out.println(card.toString());
	}
	
}
