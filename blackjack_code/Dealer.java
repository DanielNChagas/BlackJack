package blackjack;
import java.util.Iterator;

/**
 * Implements the typical behavior for a Dealer in a BlackJack game
 */
public class Dealer extends Person {

	Hand hand;
	Card hole; //dealer card that is not shown in the beginning of the game
	int totalHandCount;
	
	
	
	/**Description: Constructor of the dealer that creates a hand and card with
	 * 				default rank=0 and suit=0;
	 */
	public Dealer() {
		this.hand= new Hand(0);
		this.hole= new Card("0", '0');
	}
	
	/**Description: returns the hand of the dealer
	 * @return the hand of the dealer
	 */
	public Hand gethand() {
		return hand;
	}
	
	/**Description: returns the hole card of the dealer
	 * @return the hole card of the dealer
	 */
	public Card gethole() {
		return hole;
	}
	
	/**Description: returns the total hand count of the dealer
	 * @return the total hand count of the dealer
	 */
	public int getTotalHandCount() {
		return totalHandCount;
	}

	/**Description: Adds a card to the hand
	 * @param card- card to be added to the dealer hand
	 */
	public void addToHand(Card card) {
		this.hand.receiveCard(card);

	}

	/**Description: deals the card that is going to be hidden
	 * @param card- card that is going to be hidden
	 */
	public void addToHole(Card card) {
		this.hole=card;
	}
	
	/**Description: print to the cmd the cards that the dealer have, hiding
	 *              the hole card. Hole card is shown as X 
	 *@return - the string that shows the dealer cards 
	 */
	public String showDealerCardsWithoutHole() {
		String handDisplay= "";
		Iterator<Card> it = this.hand.cardsInHand.iterator();
		while(it.hasNext()) {
			handDisplay += it.next() + " ";		}	
		
		return("dealer's hand "+ handDisplay + "X");
	}
	
	
	/**Description: print to the screen the cards that the dealer have 
	 * 				including the hole card
	 *@return - the string that shows the dealer cards
	 */
	public String showDealerCardsWithHole() {
		return("dealer's hand " + this.hand);
	}
	
	/**Description: Add the hole card to the hand so that can be seen and 
	 * printed 
	 * @return - returns the hole card
	 */
	public Card joinDealerHole() {
		this.hand.receiveCard(this.hole);
		return this.hole;
	}
	
	
	
	/**Description: Verifies if the dealer has an Ace in the card showing after the deal
	 * 				(doesn't verify the hole card)
	 * @return - true if the dealer has a ace
	 */
	public boolean hasAceAfterDeal() {
		if(this.hand.cardsInHand.getFirst().rank.equals("A"))
			return true;
		else
			return false;
	}
	
	
	
	/**Description: clear the cards in the dealer hand and resets to zero
	 *  			the totalHandValue
	 */
	public void clearHandDealer() {
		hand.cardsInHand.clear();
		hand.totalHandValue=0;
	}
	/**Description: Verify if there is still any players in game
	 * @param players - players in the game
	 * @return - if there aren't any players in game there is no need for the dealer 
	 * 			to decide if he wants to hit or stand
	 */
	public boolean dealerCanPlay(Player [] players) {
		
		for(int i=0; players[i]!=null; ++i) {
			if(players[i].isInGame()) {
				return true;
			}
	 	}	
		return false;
	}


}