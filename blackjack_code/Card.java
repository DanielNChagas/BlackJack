package blackjack;

/**
 * Implements the typical behavior for a Card
 */
public class Card implements Cloneable{
	/**
	 * Rank associated with the card 
	 * Ranks: A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2
	 */
	String rank; 	
	/**
	 * Suit associated with the card 
	 * Suits: D->Diamonds C->Clubs S->Spades H->Hearts
	 */
	char suit;
	/**
	 * Value associated with the card
	 */
	int value;

	/**
	 * Description: Constructor of class Card
	 * @param rank - rank of the card
	 * @param suit - suit of the card
	 */
	public Card(String rank, char suit) {
		this.rank=rank;
		this.suit=suit;
		this.value=this.getValueFromRank(rank);
	}
	
	/**
	 *Description: prints the card in the format "RS" where 'R' means rank and 'S' means suit (Example: 7D)
	 */
	@Override
	public String toString() {
		return "" + rank + suit;
	}
	
	
	/**
	 * Description: Gets the value of the card
	 * @return - Integer with the value of the card
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Description: This function return the value associated to a rank assuming that figures 
	 * have value = 10 (except for Aces)
	 * 
	 * @param rank - Rank of the card
	 * @return - return integer with the value of the card
	 */
	public int getValueFromRank(String rank) {
		int value;
		try { //Tries to get the value from the rank
			value = Integer.parseInt(rank);
		}
		catch(NumberFormatException e){
			if(rank.equals("A"))
				return 11;
			 //If the rank is a figure (K, Q or J) returns the value 10
			return 10; 
		}
		return value;
	}
	
	/**
	 * Description: This function checks if a card is an Ace with value 11
	 * @param card - card that is going to be checked
	 * @return - boolean that is true if the card is an ACE and false if it isn't
	 */
	public boolean isAceAndValueEleven() {
		return(value == 11 && rank == "A") ;
	}
	
	/**
	 *Description: copies the information of a card to a new card
	 */
	@Override
	public Card clone() {
		try { Card newCard = (Card) super.clone();
		return newCard;}
		catch(CloneNotSupportedException e){System.out.println("Unable to deal a new Card"); System.exit(1);}
		return null;
		
	}
	
}
