package blackjack;

/**
 * Clusters some Player Statistic
 */
public class PlayerStats {
	private int nWins;
	private int nPushes;
	private int nLosses;
	private int nBlackjack;//number of blackjacks the player has had
	private float initialBalance;

	
	/**Description: Constructor of the PlayerStats
	 * @param initialBalance - value to be assigned to the initialBalance variable
	 */
	public PlayerStats(float initialBalance){
		this.initialBalance=initialBalance;
	}
	
	
	/**Description: Prints the stats (average number of wins, loses and pushes;average number of 
	 * 				blackjacks of the player and of the dealer; final balance and gain in relation to 
	 * 				the initial balance)
	 * @param info - GameInfo object that contains the info about the number of games
	 * @param currBalance - current balance of the player
	 * @return - string that contains the stats of the game to be printed
	 */
	public String toString(GameInfo info, float currBalance) {
		double N1;
		double N2;
		double N3;
		double N4;
		double N5;
		double N6;
		double N7;
		String string;
		
		N6=currBalance;
		N7=((currBalance-initialBalance)/initialBalance)*100;
		
		
		if(info.nGames!=0) {
			N1=(double)nBlackjack/info.nGames;
			N2=(double)info.nBlackjackDealer/info.nGames;
			N3=(double)nWins/info.nGames;
			N4=(double)nLosses/info.nGames;
			N5=(double)nPushes/info.nGames;
		}else {
			N1=N2=N3=N4=N5=0.0;
		}
		
		string= String.format("BJ P/D  %.02f/%.02f\n"
				   + "Win     %.02f \n"
				   + "Lose    %.02f\n"
				   + "Push    %.02f\n"
				   +"Balance  %.00f(%.02f %%)",N1, N2, N3,N4,N5,N6, N7);
		
				
		return string;
	}
	
	

	/**Description: Updates the number of wins, loses, pushes and blackjacks of the player
	 * @param result - result of a round (w, p, l)
	 * @param blackjack - true if the player had a blackjack
	 */
	public void updateStatsPlayer(char result, boolean blackjack) {
		
		if (result=='w'){
			this.nWins++;	
		}else if(result== 'p') {
			this.nPushes++;
		}else if(result =='l') {
			this.nLosses++;
		}
		
		if (blackjack==true) {	
			this.nBlackjack++;
		}
				
	}
	
}
