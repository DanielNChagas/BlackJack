package blackjack;

/**
 * Protocol to handle an input String obtained from the user
 */
public interface HandleInput {

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
	 * @throws InvalidInputException when the input is not in the possible Decisions or a basic option
	 */
	public boolean handleDecisionInput (GameInfo info, String inputLine,  Player player, int handNumber) throws InvalidInputException;
	
	/**
	 * Description: Handle the input string while Betting
	 * <p>
	 * This class identifies if the string is a valid command in this context.
	 * If the string is a valid command and a basic one, then handle its behavior.
	 * This class doesn't handle the 'ad' basic command, it should be done exteriorly. 
	 * <p>
	 * basic commands being considered: ('$', 'st', 'ad' and 'q')
	 * 
	 * @param info - information about the game
	 * @param inputLine - Input string split into a string array
	 * @param player - Player performing the bet
	 * 
	 * @return An integer>0 if the command is a bet command; OR An integer=-1 if a valid command was introduced, but not a bet one; OR An integer=-2 if a not valid command was introduced
	 */
	public int handleBetInput(GameInfo info, String[] inputLine, Player player);

	
}