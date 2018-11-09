package hakubishin.card_operations;

public class Player extends CardHub {
	private String playerName;
	
	public Player() {
		super();
	}
	
	public Player(String name) {
		this();
		this.playerName = name;
	}
	public Player(String name, int max) {
		super(max);
		this.playerName = name;
	}
}
