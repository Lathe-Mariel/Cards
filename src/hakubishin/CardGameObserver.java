package hakubishin;

import java.util.ArrayList;

import javax.swing.JPanel;

import hakubishin.card_operations.CardHub;
import hakubishin.card_operations.CardUtil;
import hakubishin.card_operations.Player;

public class CardGameObserver {
	Ruler ruler;
	private JPanel[] panel;
	CardHub cardsStock;
	CardPlayField frame = null;
	ArrayList<Player> players;

	public void init() {
		players = new ArrayList<Player>();
		try {
			frame = new CardPlayField();
			frame.setVisible(true);
			frame.setController(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	cardsStock = new CardHub();
	CardUtil.createNewCards(cardsStock, true);
	CardUtil.allFaceUp(cardsStock.getCardList());
	Card.setObserver(this);
	}

	public void setRuler(Ruler ruler) {
		this.ruler = ruler;
	}

	public void createPlayer(int max, String name) {
		players.add(new Player(name, max));
	}

	public void fire(Card card) {
		ruler.cardSelected(card);
	}

//	public void setPlayPanel(JPanel panel, int index) {
//		this.panel[index] = panel;
//	}

	public int provideCards(int number, int playerIndex) {
		int sended = 0;
		for(int i = 0; i < number; i++) {
			Card card = cardsStock.next();
			if(card == null)break;
			CardUtil.sendCard(card, cardsStock,  players.get(playerIndex));
			frame.addCard(playerIndex, card);
			sended++;
			try {
				Thread.sleep(Preference.waitTime);
			}catch(Exception e){}
		}
		return sended;
	}
	
	public void sendCommand(int key) {
		switch (key) {
		case 0:
			((OldMaid)(ruler)).removeSameNumbers(players.get(0).getCardList());
			break;

		default:
			break;
		}
	}
}
