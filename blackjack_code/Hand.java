package blackjack;
import java.util.*;

/**
 * Implements the typical behavior for a Hand (group of cards)
 */
public class Hand {
	
	/**
	 * List of cards in the hand
	 */
	LinkedList<Card> cardsInHand;
	
	int currbet;	//current bet of the hand
	/**
	 * Set with all the possible plays for the hand in every instant of the game
	 */
	HashSet<Character> possiblePlays;
	/**
	 * Total value in the cards hand
	 */
	int totalHandValue;
	/**
	 * variable that says if the hand is soft ('s') or hard ('h')
	 */
	char softHard;	
	/*
	 * Says if the hand has surrender (true) or not (false)
	 */
	boolean hasSurrender=false;	

	/**
	 * Description: Constructor of Hand class
	 * @param currbet - Current bet of the hand
	 */
	public Hand(int currBet) {
		this.cardsInHand = new LinkedList<Card>();
		this.currbet = currBet;
		this.totalHandValue=0;
		this.softHard = 'h';
		this.possiblePlays= new HashSet<Character>();
		//adds the initial possible plays to the hand
		this.possiblePlays.add('h');	//hit
		this.possiblePlays.add('s');	//stand
		this.possiblePlays.add('i');	//insurance
		this.possiblePlays.add('u');	//surrender
		this.possiblePlays.add('p');	//split
		this.possiblePlays.add('2');	//double
	}

	/**
	 * Description: This function adds a card to the hand, and adds the value of the card to the
	 * total value of the hand.
	 * 
	 * @param card - Card that is going to be added to the hand
	 */
	private void addToHand(Card card) {
		cardsInHand.add(card);
		this.totalHandValue += card.getValue();
	}
	
	/**Description: Doubles the value of the current bet
	 * 
	 */
	public void doubleBet() {
		this.currbet=this.currbet*2;
	}

	/**
	 * Description: This function Checks if the hand has busted (has more than 21 points)
	 * 
	 * @return Boolean that is true if the hand has busted and false if it hasn't
	 */
	public boolean checkForBust() {
		return(this.totalHandValue > 21) ;
	}
	
	
	/**
	 * Description: This function is responsible for adding the card received and for checking if the player 
	 * busts with the received card
	 * @param receivedCard - The card that is received for the hand
	 * @return - Returns a boolean that is true if the hand has busted and is false if it hasn't
	 */
	public boolean receiveCard(Card receivedCard){
		this.addToHand(receivedCard);
		while(this.checkForBust()) {
			
			if(this.checkHard()) {
				if(receivedCard.isAceAndValueEleven()) {
					((Ace) receivedCard).changeValueDown();
					this.totalHandValue -= 10;
				}
				else
					return true;
				}
			else {
				this.changeAceValue();
				this.softHard='h';
			}
		}
		if(receivedCard.isAceAndValueEleven()) {
			this.softHard='s';
			return false;
		}
		
		return false;
	}
	
	/**
	 * Description: This function check if the hand is hard or soft
	 * @return - Boolean that is true if the hand is hard and false if the hand is soft
	 */
	public boolean checkHard() {
		return (this.softHard == 'h');
	}

	/**
	 * Description: This functions searches for the 1st Ace with value 11 and changes it's value to 1
	 * 
	 */
	public void changeAceValue() {
		Iterator<Card> it = cardsInHand.iterator();
		Card currentCard;
		while(it.hasNext()) {	
			currentCard=it.next();
			if(currentCard.isAceAndValueEleven() ) {
				((Ace)currentCard).changeValueDown();
				this.totalHandValue -= 10;
				return;
			}			
		}
	}
	
	/**
	 * Description: This function checks if the hand has a pair after the player's hand has been dealt (2 cards in hand)
	 * 
	 * @return - boolean that is true if the hand has a pair and false if it hasn't
	 */
	public boolean canSplit() {
		if(this.cardsInHand.size() == 2)	
			return(this.cardsInHand.get(0).value == this.cardsInHand.get(1).value || this.cardsInHand.get(0).rank.equals(this.cardsInHand.get(1).rank));
		return false;
	}

	/**
	 * Description: This function checks if the hand has a total value of 9, 10 or 11
	 * 				and if does it means it can make a double down
	 * @return - boolean true if the hand can make a double down and false if it can't
	 */
	public boolean canDouble() {
		if(this.totalHandValue==9 ||this.totalHandValue==10 || this.totalHandValue==11)
			return true;
		else
			return false;
	}

	/**
	 *Description: prints the cards in the hand and the total value (Ex: 3D 10H (13))
	 */
	@Override
	public String toString() {
		String handDisplay= "";
		Iterator<Card> it = this.cardsInHand.iterator();
		while(it.hasNext()) {
			handDisplay += it.next() + " ";		}	
		return handDisplay + "(" + this.totalHandValue + ")";
	}
	


}