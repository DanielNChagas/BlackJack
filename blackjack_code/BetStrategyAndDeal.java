package blackjack;

/**
 * Bet and deal protocol for a specific strategy
 */
public interface BetStrategyAndDeal {
	
	/**Description: Choose the bet decision for a player, depending on the strategy
	 * 
	 * @param player - player deciding the bet
	 * @param info - information about the game
	 * 
	 * @return an int with the bet amount
	 */
	public int betDecision(Player player, GameInfo info);
	
	/**Description: Choose when to make the deal, depending on the strategy
	 * 
	 * @param players - all players in game
	 */
	public void dealDecision(Player[] players);
}
