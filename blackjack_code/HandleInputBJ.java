package blackjack;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Handle an input String obtained from the user, accordingly to the BlackJack typical commands
 */
public class HandleInputBJ implements HandleInput{
	
	/**Description: Handle an input string while a game decision/playing
	 * <p>
	 * This class identifies if the string is a valid command in this context.
	 * If the string is a valid command and a basic one, then handle its behavior.
	 * This class doesn't handle the 'ad' basic command, it should be done exteriorly.
	 * <p>
	 * basic commands being considered: ('$', 'st', 'ad' and 'q')
	 * 
	 * @param info - information about the game
	 * @param inputLine - input string to be handled
	 * @param player - player to witch the Decision will be handled
	 * @param handNumber - Number of the player's hand that is being played  
	 * 
	 * @return true if the command is one of the basic ones and false if not
	 * 
	 * @throws InvalidInputException when the input is not in the possible Decisions or a basic option ('$', 'st', 'ad' and 'q')
	 */
	@Override
	public boolean handleDecisionInput(GameInfo info, String inputLine,  Player player, int handNumber) throws InvalidInputException{
		HashSet<String> possibleDecisions = new HashSet<String>();
		possibleDecisions.add("ad");
		possibleDecisions.add("$");
		possibleDecisions.add("st");
		possibleDecisions.add("q");
		
		//Join this player's possibleDecisions to the basic options
		convertHash(player.hands.get(handNumber).possiblePlays, possibleDecisions);
		
		if(possibleDecisions.contains(inputLine)) { //Checking if the input line is a valid command
			return handleBasicOption( inputLine, player, info);
		}
		
		throw new InvalidInputException(inputLine + ": illegal command");
	}

	/**
	 * Description: Handle the input string while in Bet state
	 * <p>
	 * This class identifies if the string is a valid command in this context.
	 * If the string is a valid command and a basic one, then handle its behavior.
	 * This class doesn't handle the 'ad' basic command, it should be done exteriorly.
	 * <p>
	 * 
	 * basic commands being considered: ('$', 'st', 'ad' and 'q')
	 * 
	 * @param inputLine - Input string split into a string array
	 * @param player - Player performing the bet
	 * @param lastBet - This player's last bet
	 * 
	 * @return An integer>0 if the command is a bet command; OR An integer=-1 if a valid command was introduced, but not a bet one; OR An integer=-2 if a not valid command was introduced
	 * 
	 * 
	 */
	public int handleBetInput(GameInfo info, String[] inputLine, Player player) {
		HashSet<String> possibleDecisions = new HashSet<String>();
		possibleDecisions.add("ad");
		possibleDecisions.add("$");
		possibleDecisions.add("st");
		possibleDecisions.add("q");
		possibleDecisions.add("b");		
		
		if(possibleDecisions.contains(inputLine[0])) {
			
			handleBasicOption(inputLine[0], player, info);
			
			if(inputLine[0].equals("b")) {
				
			    	if(inputLine.length > 1){ //If more then the bet sign is introduced
			    		int amount;
			    		try { //Tries to get the bet amount
			    			amount = Integer.parseInt(inputLine[1]);
			    		}
			    		catch(NumberFormatException e){
			    			 //If the second argument was not an integer then it is interpreted as an error
			    			// and a new command is asked
			    			return -2; 
			    		}
			    		
				    	if(amount > player.maxBet) {
				    		player.lastBet = player.maxBet;
				    		return player.maxBet;
				    	}
				    	
				    	if(amount < player.minBet) {
				    		player.lastBet = player.minBet;
				    		return player.minBet;
				    	}

				    	
				    	return amount;
			    	}
			    	else {
			    		return player.lastBet;//current bet equals to last bet
			    	}
			}
		}
		else {
			return -2; //If the input was not on the possible commands, then a new input is asked
		}
		return -1; 
	}
	

	/**Description: Joins a Character HashSet to a String HashSet
	 * 
	 * @param possiblePlays - Character HashSet
	 * @param possibleDecisions - String HashSet
	 * 
	 */
	private void convertHash(HashSet<Character> possiblePlays,HashSet<String> possibleDecisions) {
		Iterator<Character> i = possiblePlays.iterator();
		while(i.hasNext()) {
			possibleDecisions.add(String.valueOf(i.next()));
		}
	}
	
	/**Description: Handle an input string comparing it with the basic options ('$', 'st', 'ad' and 'q')
	 * 
	 * @param command - The command known as a valid one (may be one of the basic ones or not)
	 * @param player - player to witch the Decision will be handled
	 * @param handNumber - Number of the player's hand that is being played  
	 * 
	 * @return true if the command is one of the basic ones and false if not
	 */
	private boolean handleBasicOption(String command, Player player, GameInfo info) {
		if(command.equals("$")) {
			System.out.println("player current balance is " + player.balance);
			return true;
		}
		else if(command.equals("st")) {
			player.showStats(info, player.balance);			
			return true;
		
		}else if(command.equals("ad")) {
			return true;
			
		}else if(command.equals("q")) {
			System.out.println("bye");
			System.exit(0);
		}
		
		return false;
	}
	
	

}