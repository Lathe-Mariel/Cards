package hakubishin;

public class OldMaid implements Ruler{
	private CardGameObserver cgo;
	
	public void start() {
		cgo.createPlayer(54, "うさぎ～ぬ");
		cgo.createPlayer(54, "ハクビ神");
		
		cgo.provideCards(26, 0);
	}
	
	public void setObserver(CardGameObserver cgo) {
		this.cgo = cgo;
	}

}
