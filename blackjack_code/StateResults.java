package blackjack;


public class StateResults implements GameState {
	
	/**Description: Determines the results of the round
	 * 
	 * @param game- game that is being played
	 */
	public void move(Game game) {
		int handIndex;
		
		for(int i=0; game.players[i]!=null; i++) {
			
			if(game.players[i].insurance && (game.dealer.haveBlackjack(game.dealer.hand))) {
				game.players[i].addToBalance(game.players[i].insuranceBet*2);
				game.printString("player wins insurance");
			}
			else if(game.players[i].insurance && !(game.dealer.haveBlackjack(game.dealer.hand))) {
				game.printString("player loses insurance");
			}
			
			
			for(handIndex=0; handIndex<game.players[i].hands.size(); handIndex++) {
				
				Hand hand= game.players[i].hands.get(handIndex);
				
				//if player surrenders the player loses
				if(hand.hasSurrender){
					game.printString(printResultConsideringSplit(game.players[i].hasMadeSplit, handIndex, game.players[i].balance, "loses"));
					game.players[i].updateResultPlayer('l', false);
				
				//if player has 21 points with 2 cards after splitting it pushes if the dealer has blackjack
				}else if(hand.totalHandValue==21 && game.players[i].hands.get(handIndex).cardsInHand.size()==2 && game.dealer.haveBlackjack(game.dealer.hand) ) {
					game.players[i].addToBalance((float)(hand.currbet));
					game.printString(printResultConsideringSplit(game.players[i].hasMadeSplit, handIndex, game.players[i].balance, "pushes"));
					game.players[i].updateResultPlayer('p', false);
				//if the player has a blackjack and the dealer doesn't	
				}else if(game.players[i].haveBlackjack(game.players[i].hands) && !(game.dealer.haveBlackjack(game.dealer.hand))) { 
					
					game.players[i].addToBalance((float)(hand.currbet*2.5));
					game.printString(printResultConsideringSplit(game.players[i].hasMadeSplit, handIndex, game.players[i].balance, "wins"));
					game.players[i].updateResultPlayer('w', true);
				
				//if the dealer has a blackjack and the player doesn't	
				}else if(!(game.players[i].haveBlackjack(game.players[i].hands)) && (game.dealer.haveBlackjack(game.dealer.hand))) { 
					
					game.printString(printResultConsideringSplit(game.players[i].hasMadeSplit, handIndex, game.players[i].balance, "loses"));
					game.players[i].updateResultPlayer('l', false);	
				
				
				// if the player has a better hand than the dealer
				}else if(hand.totalHandValue>game.dealer.hand.totalHandValue && !(hand.checkForBust())) { 			
					
					game.players[i].addToBalance((hand.currbet*2));
					game.printString(printResultConsideringSplit(game.players[i].hasMadeSplit, handIndex, game.players[i].balance, "wins"));
					game.players[i].updateResultPlayer('w', false);
				
				//if the player has 21 and only 2 cards and has made a split	
				}else if(hand.totalHandValue==21 && game.players[i].hands.get(handIndex).cardsInHand.size()==2 && !(game.dealer.haveBlackjack(game.dealer.hand)) ) { 
					
					game.players[i].addToBalance((float)(hand.currbet*2));
					game.printString(printResultConsideringSplit(game.players[i].hasMadeSplit, handIndex, game.players[i].balance, "wins"));
					game.players[i].updateResultPlayer('w', false);
					
				//if there is a push 	
				}else if(hand.totalHandValue==game.dealer.hand.totalHandValue && !(hand.checkForBust()) ) { 
					
					game.players[i].addToBalance((hand.currbet));
					game.printString(printResultConsideringSplit(game.players[i].hasMadeSplit, handIndex, game.players[i].balance, "pushes"));
					
					game.players[i].updateResultPlayer('p', game.players[i].haveBlackjack(game.players[i].hands));
				
				// if the dealer busts and the player doesn't bust and doesn't have a blackjack
				}else if(hand.totalHandValue<=21 && game.dealer.hand.checkForBust() && !(game.players[i].haveBlackjack(game.players[i].hands))) {
				
					game.players[i].addToBalance((hand.currbet*2));
					game.printString(printResultConsideringSplit(game.players[i].hasMadeSplit, handIndex, game.players[i].balance, "wins"));
					
					game.players[i].updateResultPlayer('w', false);
				//if the player loses
				}else{
					game.printString(printResultConsideringSplit(game.players[i].hasMadeSplit, handIndex, game.players[i].balance, "loses"));
					game.players[i].updateResultPlayer('l', false);
				}			
			}
			
			game.players[i].clearHands();
			game.players[i].hasMadeSplit = false;
			
				
		}
		//update the stats of the game and of the dealer
		game.info.updateStatsDealer(game.dealer.haveBlackjack(game.dealer.hand));
		game.info.updateStatsGame();
		
		game.dealer.clearHandDealer();
		    
	 }
	
	/**Description: print the result with an indication of the hand number if 
	 * 				there is more than one hand (has made a split)
	 * @param hasMadeSplit- if true it means the player has already made a split
	 * @param handIndex-index of the hand that we want to show the result
	 * @param balance- balance of the player
	 * @param result - result of the round
	 * @return
	 */
	private String printResultConsideringSplit(boolean hasMadeSplit,int handIndex, float balance, String result) {
		if(hasMadeSplit) {		
			return("player " +  result + " [" + (handIndex+1) + "] " +"and his current balance is "+ balance);
		}else {
			return("player " +  result + " and his current balance is "+ balance);
		}
	} 

}