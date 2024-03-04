package blackjack;
import java.util.ArrayList;

/**
 * Implements the typical behavior for a Player in a BlackJack game
 */
public class Player extends Person {
	
	ArrayList<Hand> hands;
	float balance;
	
	/**
	 * last result the player has had (w, l, p)
	 */
	char lastResult;
	int minBet;
	int maxBet;
	
	boolean insurance;
	
	/**
	 *  value of the bet made in the insurance
	 */
	int insuranceBet; 
	String cmdFile; 
	
	PlayingStrategy playingStrategy;
	BetStrategyAndDeal betStrategy;
	
	/**
	 *  strategy that is being used by the player in the simulation mode
	 */
	String strategy;
	
	PlayerStats playerStats;
	boolean hasMadeSplit = false;
	int lastBet;
	
	/**Description: Constructor of the Player class
	 * @param balance- money that the player has
	 * @param minBet- minimal money value that the player decides 
	 * 				  that he is going to use during the game
	 * @param maxBet- maximal money value that the player decides 
	 * 				  that he is going to use during the game
	 */
	public Player(float balance, int minBet, int maxBet) {
		this.balance=balance;
		this.minBet=minBet;
		this.maxBet=maxBet;
		this.lastBet=minBet;
		this.lastResult=0;
		this.playerStats= new PlayerStats(balance);

	}
	
	/**Description: Constructor of the Player class with a fixed strategy
	 * @param balance- money that the player has
	 * @param minBet- minimal money value that the player decides 
	 * 				  that he is going to use during the game
	 * @param maxBet- maximal money value that the player decides 
	 * 				  that he is going to use during the game
	 * @param strategy - counting and bet strategy
	 */
	public Player(float balance, int minBet, int maxBet, String strategy) {
		this(balance, minBet, maxBet);
		this.strategy=strategy;
		
	}
	
	/**Description: Constructor of the Player class with a fixed command file
	 * @param cmdFile - File with all the commands for this player
	 * @param balance - money that the player has
	 * @param minBet - minimal money value that the player decides 
	 * 				  that he is going to use during the game
	 * @param maxBet - maximal money value that the player decides 
	 * 				  that he is going to use during the game
	 */
	public Player(String cmdFile, float balance, int minBet, int maxBet) {
		this(balance, minBet, maxBet);
		this.cmdFile=cmdFile;
		
	}

	/**Description: defines the value of bet the player wants to make and creates the hand
	 * 				
	 * @param betvalue- value that the player wants to bet
	 */
	public void bet(int betvalue) {
		
		hands=new ArrayList<Hand>();
		hands.add(new Hand(betvalue));
		this.balance=this.balance-betvalue;
		this.lastBet=betvalue;
		
	}

	/**Description: doubles the value of the bet for a specific hand
	 * 				The value of the possible plays to make 
	 * 				after making a double is also changed
	 * @param hand- hand where the player wants to double the bet
	 *@return boolean- returns true if the hand has busted
	 */
	public boolean doubleDown(Hand hand, Card card) {
		
		this.balance=this.balance-hand.currbet;
		hand.doubleBet();
		
		hand.possiblePlays.clear();
		
		if(hand.receiveCard(card))
			return true;
		else
			return false;
		
	}

	/**Description: the player receives half of the value it has betted 
	 * 				in that hand and clears it.
	 * 				The value of the possible plays to make 
	 * 				after making a surrender is also changed
	 * @param hand - hand that surrenders 
	 */
	public void surrender(Hand hand) {
		this.balance=this.balance+(float)hand.currbet/(float)2.0;
		hand.hasSurrender = true;
		hand.possiblePlays.clear();
	}

	/**Description: Changes the value of the boolean insurance to true
	 * 				and takes the value of the bet of a specific hand from the
	 * 				player's balance. The value of the possible plays to make 
	 * 				after making an insurance is also changed
	 */
	public void insurance() {
		this.insurance=true;
		this.insuranceBet=(int)this.hands.get(0).currbet;
		this.balance=this.balance-this.hands.get(0).currbet;
		this.hands.get(0).possiblePlays.remove('i');
	}

	
	/** Description: split 1 hand in 2 hands and puts them both in the place of the
	 * 				 ArrayList where the the original hand was. Also gives one card to the first hand
	 * 	     		 The value of the possible plays to make 
	 *  			 after making a split is also changed
	 * @param card1 - card to be added to the first hand
	 * @param hand - hand that we want to split in two hands
	 */
	public void split(Hand hand, Card card1) {
		      
		int ind;
		
		hasMadeSplit = true;
		ind =hands.indexOf(hand);
		
								
		//if a split is made it is not possible to make
		//a insurance anymore 
		if(hand.possiblePlays.contains('i')) {
			hand.possiblePlays.remove('i');
		}
		
		
		//create the new hand with the same bet value
		hands.add(ind+1,new Hand(hand.currbet));
		//takes the value of the new bet from the player's balance
		this.balance=this.balance-hand.currbet;
		if(hands.get(ind).cardsInHand.get(0).rank.equals("A")) {	//If it is a pair of Aces
			((Ace)hands.get(ind).cardsInHand.get(0)).changeValueUp();	//changes the value of the 1st Ace from 1 to 11
			hands.get(ind+1).totalHandValue=11;	//changing the value of the total hand value to 11 in both hands
			hands.get(ind).totalHandValue=11;
		}else {//the totalHandValue gets distributed by two hands
			hands.get(ind+1).totalHandValue=hand.totalHandValue/2;
			hand.totalHandValue=hand.totalHandValue/2;
		}
		//the new hand should be soft if the 1st hand is soft and hard otherwise
		hands.get(ind+1).softHard=hand.softHard;
		hands.get(ind+1).possiblePlays.remove('i');
		
		
		//each receives one of the cards	
		hands.get(ind+1).cardsInHand.addFirst(hand.cardsInHand.getLast());
		hand.cardsInHand.removeLast();
		
		
		//After the split the first hand receive a card
		hands.get(ind).receiveCard(card1);
		
		//Verify if there are 2 cards equal and if it is possible
	    // to make a split
	    if(!(hands.get(ind).canSplit()))
	    	hands.get(ind).possiblePlays.remove('p');
	    
	    //Verify if it is possible to make a double
	    if(!(hands.get(ind).canDouble()))
	    	hands.get(ind).possiblePlays.remove('2');
	    
	    //If it were made 4 splits it is not possible to make more	    
		if(hands.size()==4)
			hands.get(ind).possiblePlays.remove('p');		
	}
	
	/**Description: Add money to the balance of the player
	 * @param gain - money to be added to the player's balance
	 */
	public void addToBalance(float gain) {
		balance=balance+gain;
		
	}
	
	/**Description: clears all player's hands 
	 * 
	 */
	public void clearHands() {
		this.hands.clear();
	}

	/**Description: Prints the player stats
	 * @param info - instance of the class GameInfo that have important information to calculate and print the player stats 
	 * 				
	 * @param currBalance - current balance of the player
	 */
	public void showStats(GameInfo info, float currBalance) {
		System.out.println(playerStats.toString(info,currBalance));
	}
	
	
	/**Description: Updates the stats of the player and the variable that saves the last result
	 * 				the player had (lastResult)
	 * @param result - result the player had (w,p,l)
	 * @param blackjack - if true it means the player had a blackjack
	 */
	public void updateResultPlayer(char result, boolean blackjack) {
		playerStats.updateStatsPlayer(result, blackjack);
		this.lastResult=result;
	}
	
	 
	/**Description: Verifies if the player is still in game, which means the player has at least
	 * 				one hand that didn't bust
	 * @return - if true it means the player is still in game
	 */
	public boolean isInGame() {
		for(int handIndex=0; handIndex<hands.size(); handIndex++) {
			if(!hands.get(handIndex).checkForBust() && !hands.get(handIndex).hasSurrender) {
				return true;
			}
		}
		return false;
	}
	
	/**Description: Defines the strategy (hilo, basic, etc..) for making game decisions (hit, stand, etc..)
	 * 
	 * @param countingStrategy - strategy to be set
	 */
	public void setPlayingStrategy(PlayingStrategy countingStrategy) {
		this.playingStrategy = countingStrategy;
	}

	/**Description: Defines the strategy for making decisions
	 * 				about the value of the bet
	 * @param betStrategy - strategy to be set
	 */
	public void setBetStrategy(BetStrategyAndDeal betStrategy) {
		this.betStrategy = betStrategy;
	}
	
}






	
