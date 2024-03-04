package blackjack;

/**
 * Implements the Standard Bet and Deal Strategy
 */
public class StandardBetAndDealStrategy implements BetStrategyAndDeal {

	/**Description: Choose the bet decision for a player
	 * 
	 * @param player - player deciding the bet
	 * @param info - information about the game
	 * 
	 * @return an int with the bet amount
	 */
	@Override
	public int betDecision(Player player, GameInfo info) {
		int bet=player.lastBet;
		if(player.lastResult == 'w') {
			bet += player.minBet;
			
			if(bet > player.maxBet) {
				bet =  player.maxBet;
			}
		}
		
		if(player.lastResult == 'l') {
			bet -= player.minBet;
				
			if(bet < player.minBet) {
				bet =  player.minBet;
			}
		}
		
		return bet;

	}
	
	/**Description: Choose when to make the deal. This strategy doesn't restrict the deal decision
	 * 
	 * @param players - all players in game
	 */
	@Override
	public void dealDecision(Player[] players){
		return;
	}

}
