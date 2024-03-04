package blackjack;

/*
 * Protocol to a game Mode
 */
public interface Mode {
	
	/**Description: Set up the Player with the right Playing and Bet Strategies 
	 * 
	 * @param player - player being set up
	 * 
	 */
	public void setupPlayer(Player player);
	
	/**Description: Set up the Shoe, choosing the right way to construct it 
	 * 
	 * @param info - information about the game
	 * @param shoe - shoe being set up
	 * 
	 */
	public void setupShoe(GameInfo info, BJShoe shoe);
	
	/**Description: Checks if the end game condition for a specific mode has been reached
	 * 
	 * @param game - the game being played
	 * 
	 * @return true if the Condition has been reached, false otherwise
	 */
	public boolean endGameCondition( Game game);


}
