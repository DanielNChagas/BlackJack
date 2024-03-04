package blackjack;

import java.util.Scanner;

/**
 * Implements the Interactive playing decision
 */
public class InteractivePlaying extends  HiloPlayingStrategy {
	
	private HandleInput handleInput;
	private Scanner terminal = new Scanner(System.in);  // Create a Scanner object
	
	
	/**
	 * Description: Constructor of the InteractivePlaying class
	 * @param handleObject - Input handler class
	 */
	public InteractivePlaying(HandleInput handleObject) {
		this.handleInput = handleObject;
	}
	
	
	/**
	 * Description: Choose the playing decision for a player
	 * 
	 * @param player - player that is taking the decision
	 * @param dealerFaceUp - dealer's face up card
	 * @param info - information of the game
	 */
	@Override
	public char gameDecision(Player player, Card dealerFaceUp, GameInfo info,int handNumber )  {
		String decision = new String();
		boolean keepAsking = true;
		
		while(keepAsking) {
			decision = terminal.nextLine();
			try { 
				
				keepAsking=handleInput.handleDecisionInput(info, decision, player,handNumber);
				if(decision.equals("ad")) {
					System.out.println("basic " + convertDecisionToString(this.basicTables(player.hands.get(handNumber), dealerFaceUp))
					+ "\nhi-lo " + convertDecisionToString(this.hiloTable(player.hands.get(handNumber), dealerFaceUp, info.trueCountHilo)));
				}
				
			}catch(InvalidInputException e){
				System.out.println(e.getMessage());
			}
		}
	
		return decision.charAt(0);	
		
	}
	
	/**Description: Converts a decision represented by a char to a decision represented by a String
	 * <p>
	 * 'h' = hit; 's'=stand; '2'=double; 'i'=insurance; 'p'=splitting; 'u'=surrender
	 * 
	 * @param decision - Decision in a char
	 * 
	 * @return String with the decision
	 */
	private String convertDecisionToString(char decision) {
		String advice= new String();
		if(decision == 'h')
			advice="hit";
		else if(decision == 's')
			advice="stand";
		else if(decision == '2')
			advice="double";
		else if(decision == 'i')
			advice="insurance";
		else if(decision == 'p')
			advice="splitting";
		else if(decision == 'u')
			advice="surrender";
		
		return advice;
	}



	

}