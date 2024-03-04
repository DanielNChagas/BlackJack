package blackjack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implements the Bet, Deal and Playing decision while in Debug Mode
 */
public class DebugBetDealPlaying implements PlayingStrategy, BetStrategyAndDeal {

	ArrayList<String> allCommands= new ArrayList<String>();
	int commandsIndex;
	HandleInput handleInput;

	HiloPlayingStrategy hiloStrategy = new HiloPlayingStrategy();
	AceFiveBetStrategy aceFiveBet;
	
	/**Description: Constructs a new DebugBetDealPlaying with a fixed cmd file and Input handler
	 * 
	 * @param fileName - command file name
	 * @param handleInput - Input handler class
	 */
	public DebugBetDealPlaying(String fileName, HandleInput handleInput) {		
		readCommandsFromFile(fileName);
		this.handleInput = handleInput;

		aceFiveBet = new AceFiveBetStrategy();
	}
	
	
	/**Description: Choose the bet decision for a player
	 * <p>
	 * Keep asking for a bet until a valid one is introduced
	 * 
	 * @param player - player deciding the bet
	 * @param info - information about the game
	 * 
	 * @return an int with the bet amount
	 */
	@Override
	public int betDecision(Player player, GameInfo info) {
		int newbet;
		String newCommand;
		
		while(true) {
			newCommand = getNextCommand();
			
		    String[] dataSplited = newCommand.split(" "); //Splits the deck into an array of chars
		    
		    newbet = handleInput.handleBetInput(info, dataSplited, player);
		    	
		    if(newbet == -1) { //
		    	if(dataSplited[0].equals("ad")) {
					System.out.println("ace-five bet " + aceFiveBet.betDecision(player, info));
		    	}
		    }
		    else if(newbet == -2) {
				System.out.println("" + newCommand + ": illegal move" );
		    }
		    else {
		    	player.lastBet = newbet;
		    	return newbet;
		    }  
		}
		//return 0;
	}

	
	/**Description: Choose the game/playing decision for a player
	 * <p>
	 * Keep asking for an input until a game move is introduced (h'; 's'; '2'; 'i'; 'p'; 'u').
	 * 
	 * @param player - player deciding the move
	 * @param dealerFaceUp - Dealer's Face up card
	 * @param info - information about the game
	 * @param handNumber - Number of the player's hand being played
	 * 
	 * @return a char with the decision
	 */
	@Override
	public char gameDecision(Player player, Card dealerFaceUp, GameInfo info, int handNumber) {
		boolean keepAsking = true;
		String decision = new String();
		
		while(keepAsking) {
			decision = getNextCommand();
			try { 
				keepAsking=handleInput.handleDecisionInput(info, decision, player,handNumber);
				if(decision.equals("ad")) {
					System.out.println("basic " + convertDecisionToString(hiloStrategy.basicTables(player.hands.get(handNumber), dealerFaceUp))
					+ "\nhi-lo " + convertDecisionToString(hiloStrategy.hiloTable(player.hands.get(handNumber), dealerFaceUp, info.trueCountHilo)));
				}
				
			}catch(InvalidInputException e){
				System.out.println(e.getMessage());
			}
		}
	
		return decision.charAt(0);
	}
	
	
	/**Description: Reads all the commands from the specified file and save them using an ArrayList
	 * 
	 * @param fileName - command file name
	 */
	public void readCommandsFromFile(String fileName) {
		try {
			//File myFile = new File(info.shoeFile);
			File myFile = new File(fileName);
			Scanner myReader = new Scanner(myFile);


			while (myReader.hasNextLine()) {
				String cmdLine = myReader.nextLine();

				String[] cmdSplited = cmdLine.split(" "); //Splits the deck into an array of chars
				for(int i=0; i< cmdSplited.length; i++) {
					if(cmdSplited[i].equals("b") && i < cmdSplited.length-1) {
						try {
							Integer.parseInt(cmdSplited[i+1]);
							allCommands.add(cmdSplited[i] + " " + cmdSplited[i+1]);

							i++;
						}
						catch(NumberFormatException e) {
							allCommands.add(cmdSplited[i]);
					
						}
					}
					else {
						allCommands.add(cmdSplited[i]);
					
					}
				}
			}
			myReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.out.println("An error occurred when reading the shoe file. "
					+ "\nThe program will end!");
			System.exit(1);
		}
		
		allCommands.add( "q");
	
	}
	
	/**Description: Reads the next command from an ArrayList that contains all the commands
	 * 
	 */
	public String getNextCommand() {
		commandsIndex++;
		return allCommands.get(commandsIndex-1);
	}
	
	/**Description: Choose when to make the deal
	 * <p>
	 * Keep waiting until a 'd' command is introduced. Let the user ask for current balance '$' and to quit the game 'q'
	 * Prints 'illegal command' when an illegal command is introduced
	 * 
	 * @param players - all players in game
	 */
	public void dealDecision(Player[] players) {
		String inputLine = new String();
		boolean keepAsking = true;
		
		
		while(keepAsking) {
		
			inputLine = this.getNextCommand();
			
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
				System.out.println(inputLine+":illegalcommand");
			}
				
		}

	}
	
	/**Description: Converts a decision represented by a char to a decision represented by a String
	 * <p>
	 * 'h' = hit; 's'=stand; '2'=double; 'i'=insurance; 'p'=splitting; 'u'=surrender
	 * 
	 * @param decision - Decision in a char
	 * 
	 * @return String with the decision
	 * 
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