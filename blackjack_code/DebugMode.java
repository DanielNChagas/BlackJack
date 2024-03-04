package blackjack;

/**
 * Set up the player and the shoe, and check end game condition considering Debug mode
 */
public class DebugMode implements Mode {


	/**Description: Set up the Player with the right Playing and Bet Strategies 
	 * 
	 * @param player - player being set up
	 * 
	 */
	@Override
	public void setupPlayer(Player player) {
		DebugBetDealPlaying debugBetAndCounting = new DebugBetDealPlaying(player.cmdFile, new HandleInputBJ());
		
		player.setPlayingStrategy(debugBetAndCounting);
		player.setBetStrategy(debugBetAndCounting);
	}

	/**Description: Set up the Shoe, choosing the right way to construct it 
	 * 
	 * @param info - information about the game
	 * @param shoe - shoe being set up
	 * 
	 */
	@Override
	public void setupShoe(GameInfo info, BJShoe shoe) {
		shoe.readShoeFromFile(info.shoeFile);
	}
	
	/**Description: Checks if the end game condition for this mode has been reached
	 * 
	 * @param game - the game being played
	 * 
	 * @return true if the Condition has been reached, false otherwise
	 */
	@Override
	public boolean endGameCondition( Game game) {
		return game.shoe.topCardPosition+1==game.shoe.shoeCards.size();
	}
}



