package blackjack;

/**
 * State protocol for the BlackJack State Machine
 */
public interface GameState {

	/**Description: executes the actions of the specified state
	 * 
	 * @param game - game that is being played
	 */
	public void move(Game game);

}