package hakubishin.card_operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import hakubishin.Card;

/**
 * This class generate Card set which used by card games.
 * When those cards are transmitted to another class which can have cards, this class must be used as like a check point.
 * @author akira
 *
 */
public class CardUtil {

	/**
	 * 
	 * @param creater Creater will have cards which this method creats.
	 * @param jokerNumber Number of joker cards which will be created.
	 */
	public void createNewCards(CardHub creater, int jokerNumber) {

		ArrayList<Card> cardSet = new ArrayList<Card>();

		for (int i = 1; i < 14; i++) {
			cardSet.add(new Card(i, "heart"));
			cardSet.add(new Card(i, "diamond"));
			cardSet.add(new Card(i, "spade"));
			cardSet.add(new Card(i, "crover"));
		}
		while (jokerNumber-- > 0 ) {
			cardSet.add(new Card());
		}
		for(Iterator<Card> i = cardSet.iterator(); i.hasNext(); ) {
			i.next().setOwner(creater);
		}
		Collections.shuffle(cardSet);
		creater.cards.clear();
		creater.cards.addAll(cardSet);
	}

	public void allFaceUp(ArrayList<Card> cards) {
		for (Iterator<Card> i = cards.iterator(); i.hasNext();) {
			i.next().setUpDown(true);
		}
	}
/**
 * When changing owner of cards, this method is the only way.
 * @param card	card which is to send.
 * @param origin	where the card send from.
 * @param destination where the card send to.
 * @return if process succeeded, this method will return true. if false is backed, it means origin doesn't have the card.
 */
	public boolean sendCard(Card card, CardHub origin, CardHub destination) {
		boolean isSuccess = origin.remove(card);
		if (isSuccess) {
			card.setOwner(destination);
			destination.add(card);
		}
		return isSuccess;
		//System.out.println(card.toString());
	}
}
