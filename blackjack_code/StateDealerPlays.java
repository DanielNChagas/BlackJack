package blackjack;

public class StateDealerPlays implements GameState {

	/**Description: Let the dealer decide if he must hit or stand
	 * @param game - game that is being played
	 */
	public void move(Game game) {
		
		Card card1;
		
		card1=game.dealer.joinDealerHole();
		
		//update the Hilo and Ace Five counts
		game.info.updateHiloCount(card1, game.shoe.remainingDecks());
		game.info.uptadeCountAceFive(card1);
		
		//When all the players have played the dealer show all his cards including the one that
		//has been hidden (the hole card)
		game.printString(game.dealer.showDealerCardsWithHole());
		
		
		//verify if there are still players in game
		//if there aren't any players in game there is no need for the dealer 
		//to decide if he wants to hit or stand
		if(!game.dealer.dealerCanPlay(game.players)) {
			return;
		}
		
		//The dealer must hit if he has a total hand value smaller than 17
		while(game.dealer.hand.totalHandValue<17) {
			
			game.printString("dealer hits");
			card1=game.shoe.dealCard();
			
			//update the Hilo and Ace Five counts
			game.info.updateHiloCount(card1, game.shoe.remainingDecks());
			game.info.uptadeCountAceFive(card1);
			
			
			//Check if the dealer busts
			if(game.dealer.hit(game.dealer.hand, card1)) {
				game.printString(game.dealer.showDealerCardsWithHole());
				game.printString("dealer busts");
				return;
			}else {
				game.printString(game.dealer.showDealerCardsWithHole());

			}
			
			
		}
				
		game.dealer.stand(game.dealer.hand);
		game.printString("dealer stands");
		
		if(game.dealer.haveBlackjack(game.dealer.hand))
			game.printString("blackjack!!");
	}
	

}