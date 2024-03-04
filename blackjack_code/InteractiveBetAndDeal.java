package blackjack;

import java.util.Scanner;

/**
 * Implements the Interactive Bet
 */
public class InteractiveBetAndDeal implements BetStrategyAndDeal {
	
	private AceFiveBetStrategy aceFive;
	private HandleInput handleInput;
	private Scanner terminal = new Scanner(System.in);  // Create a Scanner object
	
	/**
	 * Description: Constructor of the InteractiveBet class
	 * @param handleObject - Input handler class
	 */
	public InteractiveBetAndDeal(HandleInput handleObject){
		
		this.handleInput = handleObject;
		aceFive = new AceFiveBetStrategy();
	}
	
	/**Description: Choose the bet decision for a player
	 * 
	 * @param player - player deciding the bet
	 * @param info - information about the game
	 * 
	 * @return an int with the bet amount
	 */
	@Override
	public int betDecision(Player player, GameInfo info) {
		
		//Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		String userOrder;
		int newbet;
		
		while(true) { // b ou b 5
			userOrder = terminal.nextLine();  // Read user input
		    String[] dataSplited = userOrder.split(" "); //Splits the deck into an array of chars
		    newbet = handleInput.handleBetInput(info, dataSplited, player);
		    	
		    if(newbet == -1) {
		    	if(dataSplited[0].equals("ad")) {
					System.out.println("ace-five bet " + aceFive.betDecision(player, info));
		    	}
		    }
		    else if(newbet == -2) {
				System.out.println("" + userOrder + ": illegal command" );
		    }
		    else {
		    	player.lastBet = newbet;
		    	return newbet;
		    }  
		}
	}
	
	/**Description: Choose when to make the deal
	 * 
	 * @param players - all players in game
	 */
	@Override
	public void dealDecision(Player[] players) {
		String inputLine = new String();
		boolean keepAsking = true;
		
		
		while(keepAsking) {
			inputLine = terminal.nextLine();
			
			if(inputLine.equals("$")) {
				for(int i=0; players[i]!=null; ++i) {
					System.out.println("player current balance is " + players[i].balance);
				}
				
			}else if(inputLine.equals("d")) {
				keepAsking=false;
				
			}else if(inputLine.equals("q")) {
				System.out.println("bye");
				System.exit(0);
				
			}else {
				System.out.println(inputLine+":illegal command");
			}
				
		}
	}

	
}
