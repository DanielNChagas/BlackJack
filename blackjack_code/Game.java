package blackjack;
/**
 * Central class when playing BlackJack. Is the class that interacts with the user in it´s main.
 */
public class Game{

	GameState state;
	GameInfo info = new GameInfo();
	BJShoe shoe;
	Player[] players = new Player[10];
	Mode mode;
	Dealer dealer = new Dealer();
	boolean inGame;
	
	
	/**
	 * Description: Constructor of Interactive Mode
	 * 
	 * @param nDecks - number of 52-card decks in the shoe
	 * @param shuffle - percentage of shoe played before shuffling
	 */
	public Game(int nDecks, int shuffle) {
		setGameMode(new InteractiveMode());
		setGameState(new StateShuffleShoe());
		info.setInterativeVar(shuffle,true);
		
		shoe = new BJShoe(nDecks);
		mode.setupShoe(info, shoe);
	}
	
	/**
	 * Description: Constructor of Debug Mode
	 * 
	 * @param shoeFile - name of the file with the shoe
	 * @param cmdFile - name of the file with the commands
	 */
	public Game(String shoeFile) {
		setGameMode(new DebugMode());
		setGameState(new StatePlayersBet());
		info.setDebugVar(shoeFile, true);
		
		shoe = new BJShoe();
		mode.setupShoe(info, shoe);		
	}
	

	/**
	 * Description: Constructor of Simulation Mode
	 * 
	 * @param nDecks - number of 52-card decks in the shoe
	 * @param shuffle - percentage of shoe played before shuffling
	 * @param numOfShuffles - number of shuffles to perform until ending the simulation
	 * @param strategy - counting cards’ strategy to use
	 */
	public Game(int nDecks, int shuffle, int numOfShuffles) {
		setGameMode(new SimulationMode());
		setGameState(new StateShuffleShoe());
		info.setSimulationVar(shuffle, numOfShuffles, false);
		
		shoe = new BJShoe(nDecks);
		mode.setupShoe(info, shoe);			
	}
	
	
	/**
	 * Description: Create a new Player
	 * 
	 * @param balance - initial amount of money
	 * @param minBet - minimum bet
	 * @param maxBet - maximum bet
	 */
	public void createPlayer(float balance, int minBet, int maxBet) {
		players[info.numPlayers] = new Player(balance, minBet, maxBet);
		mode.setupPlayer(players[info.numPlayers]);
		info.numPlayers++;
	}
	
	/**
	 * Description: Creates a new Player with a fixed strategy
	 * 
	 * @param balance - initial amount of money
	 * @param minBet - minimum bet
	 * @param maxBet - maximum bet
	 * @param strategy - counting and bet strategy
	 */
	public void createPlayer(float balance, int minBet, int maxBet, String strategy) {
		players[info.numPlayers] = new Player(balance, minBet, maxBet, strategy);
		mode.setupPlayer(players[info.numPlayers]);
		info.numPlayers++;
	}
	
	/**Description: Creates a new Player with a fixed command file
	 * @param cmdFile - File with all the command for this player
	 * @param balance -  money that the player has
	 * @param minBet - minimal money value that the player decides 
	 * 				  that he is going to use during the game
	 * @param maxBet - maximal money value that the player decides 
	 * 				  that he is going to use during the game
	 */
	public void createPlayer( String cmdFile, float balance, int minBet, int maxBet) {
		players[info.numPlayers] = new Player(cmdFile, balance, minBet, maxBet);
		mode.setupPlayer(players[info.numPlayers]);
		info.numPlayers++;
	}

	/**
	 * Description- Set game State
	 * @param - gameState
	 */
	public void setGameState(GameState gameState) {
		state = gameState;
	}
	
	/**
	 * Description: Set game Mode
	 * @param - gameMode
	 */
	private void setGameMode(Mode gameMode) {
		mode = gameMode;
	}
	
	/**Description: runs the states in the right order
	 * 
	 */
	public void runGame() {
		
		boolean inGame = true;
	
		while(inGame) {
			
			//--------------------State ShuffleShoe---------------------------
			//this state only happens if percentage of shoe cards played is bigger
			//than the one specified in info.shuffle
			if((((float)shoe.topCardPosition)/(float)shoe.shoeCards.size())*100>info.shuffle) {
				setGameState(new StateShuffleShoe());
				state.move(this);
			}
			
			//--------------------State PlayersBet---------------------------
			setGameState(new StatePlayersBet());
			state.move(this);
			
			//--------------------State DealingCards---------------------------
			setGameState(new StateDealingCards());
			state.move(this);

			//-------------------PlayerPlays------------------------------ 
			setGameState(new StatePlayerPlays());
			state.move(this);
	
			//-------------------DealerPlays------------------------------ 
			setGameState(new StateDealerPlays());
			state.move(this);
			
			//------------------Results------------------------------------
			setGameState(new StateResults());
			state.move(this);
		
			//the game only stops if the condition for the game to end is achieved
			//this condition is different in each mode
			inGame=!(this.mode.endGameCondition(this));
		}
	}
	
	/**Description: Verifies if the game is in a mode where string toPrint
	 * 				should be printed to the terminal
	 * 
	 * @param toPrint- string to be printed
	 */
	public void printString(String toPrint) {
		
		if(info.canPrint) {
			System.out.println(toPrint);
		}
		
	}
	
	
}