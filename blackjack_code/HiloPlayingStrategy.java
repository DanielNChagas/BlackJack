package blackjack;

/**
 * Implements the Hilo Strategy
 */
public class HiloPlayingStrategy extends BasicPlayingStrategy  {
	
	/**
	 * Description: this method decides what action to take according to the Hi-lo card counting method
	 * 
	 * @param player - player deciding the action
	 * @param dealerFaceUp - dealer's face up card
	 * @param info - information of the game
	 * @param handNumber - number of the hand of the player in play
	 * @return char with the action to be taken according to the Hi-lo Strategy
	 */
	public char gameDecision(Player player, Card dealerFaceUp, GameInfo info, int handNumber) {
		return this.hiloTable(player.hands.get(handNumber), dealerFaceUp, info.trueCountHilo);
	}
	
	/**
	 * Description: This method decides the next play based on the players hand and the dealer's face up card
	 * using Hi-lo strategy's decision tables
	 * @param hand - hand in play
	 * @param dealerFaceUp - dealer's face up card
	 * @param countHilo - Hi-lo true count
	 * @return char with the action to be taken according to the Hi-lo Strategy 
	 */
	protected char hiloTable(Hand hand, Card dealerFaceUp, double countHilo) {
		if((hand.possiblePlays.contains('i')) && dealerFaceUp.rank.equals("A") && countHilo >= 3)
			return 'i';
		
		if(hand.canSplit()) {
			if(hand.totalHandValue == 20 && dealerFaceUp.value == 5) {
				if(countHilo >= 5)	//TTv5 
					return 'p';	
				else 
					return 's';
			}
			if(hand.totalHandValue == 20 && dealerFaceUp.value == 6) {
				if(countHilo >= 4)	//TTv6
					return 'p';	
				else
					return 's';
			}
		}
		if(hand.possiblePlays.contains('u')){
			if(hand.totalHandValue == 14 && dealerFaceUp.value == 10 && countHilo >= 3)
				return 'u'; //14vT
			
			else if(hand.totalHandValue == 15) {
				if((dealerFaceUp.value == 9 && countHilo >=2) || (dealerFaceUp.value == 11 && countHilo >= 1) || (dealerFaceUp.value == 10 && countHilo >= 0 && countHilo < 4))
					return 'u'; //15v9, 15v11 and 15vT
			}
		}
		if(hand.possiblePlays.contains('s')) {
			if((hand.totalHandValue == 15 && dealerFaceUp.value == 10) || (hand.totalHandValue == 16 && dealerFaceUp.value == 9))
				return this.actionOrHit(5, countHilo, 's');	//15vT and 16v9
			
			else if(hand.totalHandValue == 12 && dealerFaceUp.value == 2)
				return this.actionOrHit(3, countHilo, 's');	//12v2
			
			else if(hand.totalHandValue == 12 && dealerFaceUp.value == 3)
				return this.actionOrHit(2, countHilo, 's');	//12v3
			
			else if((hand.totalHandValue == 16 && dealerFaceUp.value == 10) || (hand.totalHandValue == 12 && dealerFaceUp.value == 4))
				return this.actionOrHit(0, countHilo, 's');	//16v10 and 12v4
			
			else if((hand.totalHandValue == 13 && dealerFaceUp.value == 2) || (hand.totalHandValue == 12 && dealerFaceUp.value == 6))
				return this.actionOrHit(-1, countHilo, 's');	//13v2 and 12v6
			
			else if((hand.totalHandValue == 12 && dealerFaceUp.value == 5) || (hand.totalHandValue == 13 && dealerFaceUp.value == 3))
				return this.actionOrHit(-2, countHilo, 's');	//12v5 and 13v3
		}
		if(hand.possiblePlays.contains('2')){
			if(hand.totalHandValue==10 && (dealerFaceUp.value == 11 ||dealerFaceUp.value == 10)) {
				return this.actionOrHit(4, countHilo, '2');	//10vT and 10vA
			}
			else if(hand.totalHandValue==9 && dealerFaceUp.value == 7)
				return this.actionOrHit(3, countHilo, '2');	//9v7
			
			else if(((hand.totalHandValue==11 && dealerFaceUp.value == 11) ||(hand.totalHandValue==9 && dealerFaceUp.value == 2)))
				return this.actionOrHit(1, countHilo, '2'); //11vA and 9v2
		}
		return this.basicTables(hand, dealerFaceUp);
	}
	
	/**
	 * Description: Checks if the minimum count for the action is respected, if it is the action is returned, if it isn't
	 * the 'hit' action is returned
	 * 
	 * @param minCountForAction - minimum Hi-lo true count to do the action
	 * @param countHilo - Hi-lo true count
	 * @param action - action that is meant to be done
	 * @return - char with the action to be taken
	 */
	private char actionOrHit(int minCountForAction, double countHilo, char action) {
		if(countHilo >= minCountForAction)
			return action;
		else
			return 'h';
	}
}