package hakubishin;

public class Main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		CardGameObserver go = new CardGameObserver();
		OldMaid baba = new OldMaid();
		go.init();
		baba.setObserver(go);
		go.setRuler(baba);
		baba.start();
	}

}
