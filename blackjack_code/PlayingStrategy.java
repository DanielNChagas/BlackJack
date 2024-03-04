package blackjack;
/**
 * Protocol for a Playing Strategy, where the next move is choose
 */
public interface PlayingStrategy {

	/**Description: Choose the game/playing decision for a player
	 * 
	 * @param player - player deciding the move
	 * @param dealerFaceUp - Dealer's Face up card
	 * @param info - information about the game
	 * @param handNumber - Number of the player's hand being played
	 * 
	 * @return a char with the decision
	 */
	public char gameDecision(Player player, Card dealerFaceUp, GameInfo info, int handNumber);

}