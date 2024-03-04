package blackjack;
/**
 * Set up the player and the shoe, and check end game condition considering Interactive mode
 */
public class SimulationMode implements Mode {


	/**Description: Set up the Player with the right Playing and Bet Strategies 
	 * 
	 * @param player - player being set up
	 * 
	 */
	@Override
	public void setupPlayer(Player player) {
		String[] modesSplited = player.strategy.split("-");
		
		if(modesSplited[0].contentEquals("BS") && modesSplited.length == 1) {
			player.setPlayingStrategy(new BasicPlayingStrategy());
			player.setBetStrategy(new StandardBetAndDealStrategy());
			return;
		}
		
		if(modesSplited[0].contentEquals("HL") && modesSplited.length == 1) {
			player.setPlayingStrategy(new HiloPlayingStrategy());
			player.setBetStrategy(new StandardBetAndDealStrategy());
			return;
		}
		
		if(modesSplited[0].contentEquals("BS") && modesSplited[1].contentEquals("AF") && modesSplited.length == 2){
			player.setPlayingStrategy(new BasicPlayingStrategy());
			player.setBetStrategy(new AceFiveBetStrategy());
			return;
		}
		
		if(modesSplited[0].contentEquals("HL") && modesSplited[1].contentEquals("AF") && modesSplited.length == 2){
			player.setPlayingStrategy(new HiloPlayingStrategy());
			player.setBetStrategy(new AceFiveBetStrategy());
			return;
		}
		
		System.out.println("Error when choosing player's Strategies. Unnable to proced.");
		System.exit(1);
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
		
	}
	
	/**Description: Checks if the end game condition for this mode has been reached
	 * 
	 * @param game - the game being played
	 * 
	 * @return true if the Condition has been reached, false otherwise
	 */
	@Override
	public boolean endGameCondition( Game game) {
		if(game.info.currNumberOfShuffles>game.info.numOfShuffles){
			game.players[0].showStats(game.info, game.players[0].balance);
			return true;
		
		}else 
			return false;
	}
		

}
