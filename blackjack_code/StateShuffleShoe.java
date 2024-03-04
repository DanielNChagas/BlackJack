package blackjack;

public class StateShuffleShoe implements GameState {

	/**Description: Shuffle the shoe
	 * 
	 * @param game- game that is being played
	 */
	public void move(Game game) {
		game.printString("shuffling the shoe");
		game.shoe.shuffleShoe();
		game.info.increaseCurrNumberShuffles();
		game.info.resetStrategyCounts();
	}

}