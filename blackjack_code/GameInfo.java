package blackjack;

/**
 * Clusters some game variables, needed all around the game
 */
public class GameInfo {

	/*
	 * count divided by the number of decks remaining
	 */
	double trueCountHilo;
	double runningCountHilo;
	int countAceFive;
	String shoeFile;
	int shuffle=100;
	/*
	 * number of shuffles that should be done before the simulation ends
	 */
	int numOfShuffles; 
	int numPlayers;
	/*
	 * is true when using a mode that must use prints (ex: debug and iterative) and false otherwise (ex: simulation)
	 */
	boolean canPrint;
	/*
	 * number of games already played
	 */
	int nGames;
	/*
	 * number of blackjacks the dealer has
	 */
	int nBlackjackDealer;
	/*
	 * number of shuffles already made
	 */
	int currNumberOfShuffles;
	
	
	
	/**Description: increase the variable that tells the number of shuffles
	 * 				already made
	 * 
	 */
	public void increaseCurrNumberShuffles() {
		currNumberOfShuffles++;
	}
	
	/**Description: Change GameInfo variables used when in Interactive Mode
	 *
	 * @param shuffle - percentage of shoe played before shuffling
	 */
	public void setInterativeVar(int shuffle, boolean canPrint) {
		this.shuffle = shuffle;
		this.canPrint = canPrint;
	}
	
	/**
	 * Description: Change GameInfo variables used when in Debug Mode
	 * 
	 * @param shoeFile - name of the file with the shoe
	 * @param cmdFile - name of the file with the commands
	 */
	public void setDebugVar(String shoeFile, boolean canPrint) {
		this.shoeFile = shoeFile;
		this.canPrint = canPrint;
	}
	
	/**
	 * Description: Change GameInfo variables used when in S Mode
	 * 
	 * @param shuffle - percentage of shoe played before shuffling
	 * @param numOfShuffles - number of shuffles to perform until ending the simulation
	 * @param strategy - counting cards strategy to use
	 */
	public void setSimulationVar(int shuffle, int numOfShuffles, boolean canPrint) {
		this.shuffle = shuffle;
		this.numOfShuffles = numOfShuffles;
		this.canPrint = canPrint;
		
	}
	
	/**Description: updates the ace five count considering the card that has been dealt from the shoe
	 * @param card - card that has been dealt from the shoe
	 */
	public void uptadeCountAceFive(Card card){
		if (card.rank.equals("A")) {
			countAceFive--;
		}
		if (card.rank.equals("5")) {
			countAceFive++;
		}
	}
	
	/**Description: updates the hilo count considering the card that has been dealt from the shoe
	 * @param card - card that has been dealt from the shoe
	 * @param decksRemaining - number of decks remaining in the shoe
	 */
	public void updateHiloCount(Card card, double decksRemaining) {
		if(card.value < 7) {
			runningCountHilo ++;
			trueCountHilo=runningCountHilo/decksRemaining;
		}
			
		else if (card.value > 9) {
			runningCountHilo --;
			trueCountHilo=runningCountHilo/decksRemaining;
		}
			
	}
	
	
	/**Description: resets the hilo and ace five count to zero
	 * 
	 */
	public void resetStrategyCounts() {
		runningCountHilo = trueCountHilo = countAceFive = 0;
	}
	
	/**Description: update the number of blackjacks the dealer had 
	 * @param blackjack - if true it means the dealer had another blackjack
	 */
	public void updateStatsDealer(boolean blackjack) {
		
		if (blackjack==true)
			this.nBlackjackDealer++;	
				
	}
	
	/**Description: update the variable that saves the number of games played
	 * 
	 */
	public void updateStatsGame() {
		this.nGames++;		
	}


}
