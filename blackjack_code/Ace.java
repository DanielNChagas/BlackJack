package blackjack;
/**
 * Represents an Ace Card
 */
public class Ace extends Card {
	
	private int[] possibleValues= {1,11};
	
	/**Description: Constructor of Ace that creates a card with rank= A
	 * @param suit - D:Diamonds C:Clubs S:Spades H:Hearts
	 */
	public Ace(char suit) {
		super("A",suit);
	}
	
	/**Description: Changes the value of the Ace card to 1
	 * 
	 */
	public void changeValueDown() {
		this.value = this.possibleValues[0];
	}
	
	/**Description: Changes the value of the Ace card to 11
	 * 
	 */
	public void changeValueUp() {
		this.value = this.possibleValues[1];
	}
	
	
}	