package blackjack;

/**
 * Set up the player and the shoe, and check end game condition considering Interactive mode
 */
public class InteractiveMode implements Mode {

	/**Description: Set up the Player with the right Playing and Bet Strategies 
	 * 
	 * @param player - player being set up
	 * 
	 */
	@Override
	public void setupPlayer(Player player) {
		player.setPlayingStrategy(new InteractivePlaying(new HandleInputBJ()));
		player.setBetStrategy(new InteractiveBetAndDeal( new HandleInputBJ()));
	}

	/**Description: Set up the Shoe, choosing the right way to construct it 
	 * 
	 * @param info - information about the game
	 * @param shoe - shoe being set up
	 * 
	 */
	@Override
	public void setupShoe(GameInfo info, BJShoe shoe) {
		shoe.createShoe();
		System.out.println("shuffling the shoe...");
	}
	
	
	/**Description: Checks if the end game condition for this mode has been reached
	 * 
	 * @param game - the game being played
	 * 
	 * @return true if the Condition has been reached, false otherwise
	 */
	@Override
	public boolean endGameCondition( Game game) {
		return false;
	}
	
	
}