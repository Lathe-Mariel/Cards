package hakubishin.card_operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import hakubishin.Card;
import hakubishin.NumberCard;

public class CardUtil {

	public static void createNewCards(CardHub creater, boolean hasJoker) {

		ArrayList<Card> cardSet = new ArrayList<Card>();

		for (int i = 1; i < 14; i++) {
			cardSet.add(new NumberCard(i, "heart"));
			cardSet.add(new NumberCard(i, "diamond"));
			cardSet.add(new NumberCard(i, "spade"));
			cardSet.add(new NumberCard(i, "crover"));
		}
		if (hasJoker) {
			cardSet.add(new Card());
			cardSet.add(new Card());
		}
		for(Iterator<Card> i = cardSet.iterator(); i.hasNext(); ) {
			i.next().setOwner(creater);
		}
		Collections.shuffle(cardSet);
		creater.cards.clear();
		creater.cards.addAll(cardSet);
	}

	public static void allFaceUp(ArrayList<Card> cards) {
		for (Iterator<Card> i = cards.iterator(); i.hasNext();) {
			i.next().setUpDown(true);
		}
	}
/**
 * 
 * @param card
 * @param origin
 * @param destination
 * @return if process succeeded, this method will return true. if false is backed, it means origin doesn't have the card
 */
	public static boolean sendCard(Card card, CardHub origin, CardHub destination) {
		boolean isSuccess = origin.remove(card);
		if (isSuccess) {
			card.setOwner(destination);
			destination.add(card);
		}
		return isSuccess;
		//System.out.println(card.toString());
	}

}
