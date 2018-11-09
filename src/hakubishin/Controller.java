package hakubishin;

public class Controller {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		CardGameObserver go = new CardGameObserver();
		go.init();
		OldMaid baba = new OldMaid();
		baba.setObserver(go);
		baba.start();
		go.setRuler(baba);
	}

}
