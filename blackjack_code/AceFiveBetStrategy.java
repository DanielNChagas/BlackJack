package blackjack;
/**
 * Implements the AceFive Strategy
 */
public class AceFiveBetStrategy implements BetStrategyAndDeal {

	/**Description: Choose the bet decision for a player using the AceFive Strategy
	 * 
	 * @param player - player deciding the bet
	 * @param info - information about the game
	 * 
	 * @return an int with the bet amount
	 */
	@Override
	public int betDecision(Player player, GameInfo info) {
		int bet;
		if(info.countAceFive >= 2) {
			bet = 2*player.lastBet;
			if(bet > player.maxBet) {
				bet = player.maxBet;
			}
		}
		else {
			bet = player.minBet;
		}
		
		return bet;
	}
	
	/**Description: Choose when/how to make the deal
	 * 
	 * @param players - all players in game
	 */
	public void dealDecision(Player[] players){
		return;
	}

}
