package blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 * Implements the typical behavior for a BlackJack shoe
 */
public class BJShoe {

	int nDecks;	//number of decks contained in the shoe
	private static String[] availableRanks = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"}; // J->Jack Q->Queen K->King A->Ace
	private static char[] availableSuits = {'S','C','H','D'}; //suit: D->Diamonds C->Clubs S->Spades H->Hearts
	ArrayList<Card> shoeCards;
	int topCardPosition;
	
	
	/**
	 * Description: Constructor of class BJShoe for interactive and simulation modes
	 * 
	 * @param nDecks - number of decks the shoe will have
	 */
	public BJShoe(int nDecks) {
		this.nDecks = nDecks;
		this.shoeCards = new ArrayList<Card>(52*nDecks);
		this.topCardPosition = 0; 
	}
	
	/**
	 * Description: Constructor of class BJShoe for Debug mode
	 * 
	 */
	public BJShoe() {
		this.shoeCards = new ArrayList<Card>();
		this.topCardPosition = 0; 
	}
	
	/**
	 * Description: This method creates and shuffles the shoe by creating nDecks 
	 * decks and concatenating them
	 */
	public void createShoe() {	
		for(int n=0 ; n<this.nDecks ; n++) {
			this.createDeck();
		}
		this.shuffleShoe();
		
	}
	
	/**
	 * Description: This method reads and creates a shoe from a file
	 * 
	 * @param fileName - name of the file where the shoe is saved
	 */
	public void readShoeFromFile(String fileName) {
		//int amount;
		try {
			File myFile = new File(fileName);
			Scanner myReader = new Scanner(myFile);
			String cardRank;
			char cardSuit;
			
			while (myReader.hasNextLine()) {
				String allShoe = myReader.nextLine();

				String[] allCards = allShoe.split(" "); //Splits the deck into an array of chars
				//System.out.println("Tamanho do deck = " + dataSplited.length);
				for(int i=0; i< allCards.length; i++) {
					String thisCard = allCards[i];
					if(thisCard.length() == 2) {
						cardRank = thisCard.substring(0, 1);
						cardSuit = thisCard.charAt(1);						
					}
					
					else if(thisCard.length() == 3) {
						cardRank = thisCard.substring(0, 2);
						cardSuit = thisCard.charAt(2);
					}
					else {
						continue;
					}
					
					if(cardRank.equals("A"))
						shoeCards.add(new Ace(cardSuit));
					else
						shoeCards.add(new Card(cardRank, cardSuit)); 
				}

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.out.println("An error occurred when readding the shoe file. "
					+ "\nThe program will end!");
			System.exit(1);
		}
	}

	/**
	 * Description: This method deals the Card by returning the card in the top position of the shoe and
	 * changes the top position of the shoe to the next card.
	 * 
	 * @return - Returns the card that was on top of the shoe
	 */
	public Card dealCard() {
		this.topCardPosition++;
		Card newCard = (Card)(this.shoeCards.get(this.topCardPosition-1).clone());
		return newCard;

	}
	
	
	/**
	 * Description: This method shuffles the order of the cards in the shoe and resets the top position 
	 * of the shoe to the top of the arrayList containing the cards in the shoe
	 */
	public void shuffleShoe() {
		Collections.shuffle(shoeCards);
		this.topCardPosition = 0;
		
	}

	
	/**
	 * Description: This method creates a deck of 52 cards, card by card and adds them to the shoe
	 */
	public void createDeck() {
		int value=1;
		for(String t : BJShoe.availableRanks) {
			if(value<10)
				value++;
			for(char s : BJShoe.availableSuits) {
				if(t=="A")
					shoeCards.add(new Ace(s));
				else
					shoeCards.add(new Card(t,s));
			}
		}
	}
	
	/**
	 * Description: This method computes the number of decks remaining in play
	 * 
	 * @return a double with the number of decks remaining
	 */
	public double remainingDecks(){
		return Math.ceil((double)(this.shoeCards.size()-this.topCardPosition)/52);
	}
	
	
	@Override
	public String toString() {
		return "" + shoeCards;
	}

}
