package blackjack;

public class StatePlayersBet implements GameState {

	/**Description: allows the players to make the bets
	 * @param game - game that is being played
	 */
	public void move(Game game) {
		int betValue;
		
		//For each player in this game
		for(int i=0; game.players[i]!=null; ++i) {
			betValue=game.players[i].betStrategy.betDecision(game.players[i], game.info);
		
		    game.players[i].bet(betValue);
		    game.printString("player is betting " + game.players[i].hands.get(0).currbet);
	 	}
	}

}