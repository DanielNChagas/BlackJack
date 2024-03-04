package blackjack;

public class StateDealingCards implements GameState {
	
	
	/**Description: deals the cards after all the players have pressed d
	 * @param game - game that is being played
	 */
	public void move(Game game) {
		Card card1;
		
		//All the players must press d before the cards are dealt
		for(int i=0; game.players[i]!=null; ++i) {
			game.players[i].betStrategy.dealDecision(game.players);
		}
		
		//deal the cards for the dealer
		card1=game.shoe.dealCard();
		game.dealer.addToHand(card1);
		
		//update the Hilo and Ace Five counts
		game.info.updateHiloCount(card1, game.shoe.remainingDecks());
		game.info.uptadeCountAceFive(card1);
		
		game.dealer.addToHole(game.shoe.dealCard());
		game.printString(game.dealer.showDealerCardsWithoutHole());
		
		//deal cards for the players
		for(int i=0; game.players[i]!=null; ++i) {

			card1=game.shoe.dealCard();
			game.players[i].hands.get(0).receiveCard(card1);
		    
		    //update the Hilo and Ace Five counts
		    game.info.updateHiloCount(card1, game.shoe.remainingDecks());
			game.info.uptadeCountAceFive(card1);
		    
			
			card1=game.shoe.dealCard();
			game.players[i].hands.get(0).receiveCard(card1);
			
			//update the Hilo and Ace Five counts
		    game.info.updateHiloCount(card1, game.shoe.remainingDecks());
			game.info.uptadeCountAceFive(card1);
					    
		    //Verify if there are 2 cards equal and if it is possible
		    // to make a split
		    if(!(game.players[i].hands.get(0).canSplit()))
		    	game.players[i].hands.get(0).possiblePlays.remove('p');
		    
		    //Verify if the dealer has a Ace in the hand
		    //if the dealer doesn't have an Ace that means
		    //the player cannot make a insurance
		    if(!(game.dealer.hasAceAfterDeal()))
		    	game.players[i].hands.get(0).possiblePlays.remove('i');  
		    
		    //Verify if it is possible to make a double
		    if(!(game.players[i].hands.get(0).canDouble()))
		    	game.players[i].hands.get(0).possiblePlays.remove('2');
		    
    
			game.printString("player's hand "+game.players[i].hands.get(0));
			
			
	 	}
	}

}