package hakubishin;

public class MemoryCards_Main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		CardGameObserver go = new CardGameObserver();
		MemoryCards memo = new MemoryCards();
		go.init();
		memo.setObserver(go);
		go.setRuler(memo);
		memo.init();
	}

}
