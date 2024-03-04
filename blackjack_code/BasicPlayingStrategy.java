package blackjack;
/**
 * Implements the Basic Playing Strategy, that chooses the right move to take
 * <p>
 * Possible moves: 'h', 's', 'p', '2', 'u'
 * 
 */
public class BasicPlayingStrategy implements PlayingStrategy {
	
	/**
	 * Label:	'h' - Hit.
	 * 			's' - Stand
	 * 			'p' - Split
	 * 			'2' - If possible double, otherwise hit
	 * 			'd' - If possible double, otherwise stand
	 * 			'u' - If possible surrender, otherwise hit
	 */
	
	/**
	 * Table with the decisions for basic strategy based on players and dealers cards when player has a pair
	 */
	private char[][] pairTable = {
	//Dealer 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 , A		Pairs
			{'p','p','p','p','p','p','p','p','p','p'},	//(A,A)
			{'h','h','p','p','p','p','h','h','h','h'},	//(2,2)
			{'h','h','p','p','p','p','h','h','h','h'},	//(3,3)
			{'h','h','h','h','h','h','h','h','h','h'},	//(4,4)
			{'2','2','2','2','2','2','2','2','h','h'},	//(5,5)
			{'h','p','p','p','p','h','h','h','h','h'},	//(6,6)
			{'p','p','p','p','p','p','h','h','h','h'},	//(7,7)
			{'p','p','p','p','p','p','p','p','p','p'},	//(8,8)
			{'p','p','p','p','p','s','p','p','s','s'},	//(9,9)
			{'s','s','s','s','s','s','s','s','s','s'},	//(10,10)
	};
	/**
	 * Table with the decisions for basic strategy based on players and dealers cards when player has a soft hand
	 */
	private char[][] softTable = {
	//Dealer 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 , A	   	Player's hand value	
			{'h','h','h','2','2','h','h','h','h','h'},	//13 or 14
			{'h','h','2','2','2','h','h','h','h','h'},	//15
			{'h','h','2','2','2','h','h','h','h','h'},	//16
			{'h','2','2','2','2','h','h','h','h','h'},	//17
			{'s','d','d','d','d','s','s','h','h','h'},	//18
			{'s','s','s','s','s','s','s','s','s','s'},	//19 to 21			
	};
	/**
	 * Table with the decisions for basic strategy based on players and dealers cards when player has a hard hand
	 */
	private char[][] hardTable = {
	//Dealer 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 , A	   	Player hand value	
			{'h','h','h','h','h','h','h','h','h','h'},	//5 to 8
			{'h','2','2','2','2','h','h','h','h','h'},	//9
			{'2','2','2','2','2','2','2','2','h','h'},	//10
			{'2','2','2','2','2','2','2','2','2','h'},	//11
			{'h','h','s','s','s','h','h','h','h','h'},	//12
			{'s','s','s','s','s','h','h','h','h','h'},	//13
			{'s','s','s','s','s','h','h','h','h','h'},	//14
			{'s','s','s','s','s','h','h','h','u','h'},	//15
			{'s','s','s','s','s','h','h','u','u','u'},	//16
			{'s','s','s','s','s','s','s','s','s','s'},	//17 to 21
	};
	
	/**
	 * Description: This method returns the decision that the player should make using the basic strategy
	 * 
	 * @param player - player that is make the decision
	 * @param dealerFaceUp - dealer's face up card
	 * @param info - information about the game
	 */
	@Override
	public char gameDecision(Player player, Card dealerFaceUp, GameInfo info, int handNumber) {
		return basicTables(player.hands.get(handNumber), dealerFaceUp);
	}

	/**
	 * Description: This method decides the next play based on the players hand and the dealer's face up card
	 * using basic strategy's decision tables
	 * 
	 * @param hand - Hand of cards of the player
	 * @param dealerFaceUp - Card that the dealer has face up
	 */
	protected char basicTables(Hand hand, Card dealerFaceUp) {
		char decision;
		if(hand.canSplit()) {	// If the hand has a pair -> use split table
			decision = pairTable[hand.cardsInHand.get(0).value-1][dealerFaceUp.value-2];
		}
		else if(!hand.checkHard()) { // If the hand is soft -> use soft table
			if(hand.totalHandValue < 15) 
				decision = softTable[0][dealerFaceUp.value-2];
			else if(hand.totalHandValue > 18)
				decision = softTable[5][dealerFaceUp.value-2];
			else
				decision = softTable[hand.totalHandValue-14][dealerFaceUp.value-2];
		}
		else { // If the hand is hard -> use hard table
			if(hand.totalHandValue < 9) 
				decision = hardTable[0][dealerFaceUp.value-2];
			else if(hand.totalHandValue > 16)
				decision = hardTable[9][dealerFaceUp.value-2];
			else
				decision = hardTable[hand.totalHandValue-8][dealerFaceUp.value-2];
		}
		
		if (decision == '2' && (!hand.possiblePlays.contains('2')))	// if the decision is to double and player can't double
			return 'h';	//player hits
		else if(decision == 'd' && (!hand.possiblePlays.contains('2')))	// if the decision is to double and player can't double
			return 's';	//player stands
		else if(decision == 'u' && (!hand.possiblePlays.contains('u')))	// if the decision is to surrender and player can't surrender
			return 'h';	//player hits
		
		if(decision == 'd')
			decision='2';
		
		return decision;
	}

}