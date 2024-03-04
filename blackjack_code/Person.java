package blackjack;

import java.util.ArrayList;

/**
 * Implements the typical behavior of a person (ex: player, dealer...) in a BlackJack game
 */
public class Person {
	
	/**Description: stand and not take anymore cards in a specific hand
	 * @param hand - hand that we want to stand and not take anymore cards
	 */
	public void stand(Hand hand) {
		
		hand.possiblePlays.clear();
	};
	
	
	/**Description: adds a card to the hand and returns true if the 
	 * 				hand has busted 
	 * @param hand - hand that is going to receive the card
	 * @param card - card to be added to the hand
	 * @return - Returns a boolean that is true if the hand has busted 
	 *          and is false if it hasn't
	 */
	public boolean hit(Hand hand, Card card) {
		
		if(!(hand.receiveCard(card))){
			hand.possiblePlays.clear();
			hand.possiblePlays.add('h');
			hand.possiblePlays.add('s');
			return false;
		}else{
			hand.possiblePlays.clear();
			return true;
		}

	};
	
	
	/** Description: Verifies if the player has a blackjack (an Ace and a 10 points card)
	 * 				It is also verified if the player only has one hand (necessary condition to have a blackjack)
	 * @param hands - array list of hands to verify
	 * @return - true if it has a blackjack
	 */
	public boolean haveBlackjack(ArrayList<Hand> hands) {
		
		if(hands.size()==1) {
			return haveBlackjack(hands.get(0));
		}else {
			return false;
		}
	}		
	
	
	/** Description: Verifies if the hand of the dealer or the player has a blackjack 
	 * 				(an Ace and a 10 points card)
	 * @param hand - hand to verify if it has an Ace and a 10 point card
	 * @return - true if it has a blackjack
	 */
	public boolean haveBlackjack(Hand hand) {
		if(hand.totalHandValue==21 && hand.cardsInHand.size()==2) {
			return true;
		}else {
			return false;
		}
	}		



	


}
