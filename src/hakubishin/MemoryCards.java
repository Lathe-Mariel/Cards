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

	public MemoryCards() {
		processCards = new ArrayList<Card>();
		relationNumber = 2;
		field = new CardHub();
	}

	public void setObserver(CardGameObserver cgo) {
		this.cgo = cgo;
	}

	@Override
	public void run() {

	}

	@Override
	public synchronized void cardSelected(Card card) {
		// TODO 自動生成されたメソッド・スタブ
		if (process == true) {
			card.setClicked(false);
			return;}
		process = true;
		if (card.isFront() == true) {
			process = false;
			return;
		}
		System.out.println(processCards.size());

		card.flip();
		//		try {
		//		Thread.sleep(700);}catch(Exception e) {e.printStackTrace();}
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
		//if (checkSameCards()) ;
		//turn++;
		//process = false;
	}

	private boolean checkSameCards() {
		if (processCards.size() != 2) {
			process = false;
			return false;
		} else {
			try {
				Thread.sleep(900);
			}catch(Exception e) {e.printStackTrace();}
			Card card1 = processCards.get(0);
			Card card2 = processCards.get(1);
			System.out.println("card1: " + card1.toString() + "    :    card2: " + card2.toString());
			if (card1.getNumber() == card2.getNumber()) {
				cgo.sendCard(card1, card1.getOwner(), cgo.players.get(turn));
				cgo.sendCard(card2, card2.getOwner(), cgo.players.get(turn));
				processCards.clear();
				process = false;
				return true;
			}
			closeCardFlip();
			process = false;
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
						cgo.frame.createSmallField(2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception f) {
			f.printStackTrace();
		}
		cgo.frame.createCenterField(3);//this number must consists of field(it means players + 1)
		cgo.createPlayer("", true);//field
		cgo.createPlayer("うさぎ～ぬ", true);//user1
		cgo.createPlayer("ハクビ神", true);//com1
		//cgo.createPlayer( "宅急便のネコ", true);

		start();
	}

	@Override
	public void start() {
		// TODO 自動生成されたメソッド・スタブ
		cgo.provideCards(54, 0, false);

		turn = 1;
		cgo.frame.setTurn(1);
	}

	@Override
	public void pushButton1() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void pushButton2() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
