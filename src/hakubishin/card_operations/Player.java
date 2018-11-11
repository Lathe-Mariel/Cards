package hakubishin.card_operations;



public class Player extends CardHub {
	private String playerName;
	private boolean human;

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

	public Player(String name, int max, int playerNumber, boolean isHuman) {
		this(name, max);
		super.setNumber(playerNumber);
		this.human = isHuman;
	}
	public void setHuman(boolean b) {
		human = b;
	}
	public boolean isHuman() {
		return human;
	}
	public String getPlayerName() {
		return playerName;
	}
}
