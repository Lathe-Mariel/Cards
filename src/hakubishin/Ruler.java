package hakubishin;
/**
 * Developers can extend this class to make a class which rules each card game.
 * All abstract methods will be called by CardGameObserver.
 * And this interface extends Runnable, so pubclic void run should be also overrided.
 * @author akira
 *
 */
public interface Ruler extends Runnable{
/**
 *	This method will be called by Card object via CardgameObserver.
 *	When Card object is clicked, the object trigger this method in their event handler.
 * @param card	It prefers to a card which called this method.
 */
	void cardSelected(Card card);

/**
 * Developer may describe game starting process in this method.
 */
	void start();

/**
 * If GUI event occurs in CardPlayField, the eventhandler call this method via CardGameObserver.
 */
	void pushButton1();
/**
 * If GUI event occurs in CardPlayField, the eventhandler call this method via CardGameObserver.
 */
	void pushButton2();
	/**
	 *  If GUI event occurs in CardPlayField, the eventhandler call this method via CardGameObserver.
	 *  This method can use arg.
	 * @param number Developer can use this integer primitive for transmites information from GUI object to Ruler.
	 */
	void numberCommand(int number);
}
