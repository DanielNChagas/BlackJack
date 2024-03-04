package blackjack;

public class StatePlayerPlays implements GameState {

	/**Description: Let the players execute their actions (hit, stand, split...)
	 * 
	 * @param game- game that is being played
	 */
	public void move(Game game) {
		
		int handIndex=0;
		char decision;
		Card card1;
		
		//For each player in this game
		for(int i=0; game.players[i]!=null; i++) {
						
						
			//For each hand of this player
			for(handIndex=0; handIndex<game.players[i].hands.size(); handIndex++) {
				
				Hand hand= game.players[i].hands.get(handIndex); //Get the hand to be played
										
				if(game.players[i].hasMadeSplit) {
										
					if(hand.cardsInHand.size()==1) { //When the hand is a result of a split and
													//hasn't received the second card
						
						card1=game.shoe.dealCard();
						//update the Hilo and Ace Five counts
					    game.info.updateHiloCount(card1, game.shoe.remainingDecks());
					    game.info.uptadeCountAceFive(card1);
					    
					    hand.receiveCard(card1);
					    
					    //Verify if in the new hand it is possible to make a split
					    //if not make it impossible to make a split
					    if(!(hand.canSplit()))
					    	hand.possiblePlays.remove('p');
					    //Verify if it is possible to make a double
					    if(!(hand.canDouble()))
					    	hand.possiblePlays.remove('2');
					    
					}
					
					game.printString("playing "+ ordinal((handIndex+1)) + " hand...");
					game.printString("player's hand ["+ (handIndex+1) +"] "+hand);
						
				}
				
				
				
				while(hand.possiblePlays.size()>0) { //while there is still possible actions the player can make
		
					decision=game.players[i].playingStrategy.gameDecision(game.players[i], game.dealer.hand.cardsInHand.get(0)
							,game.info,handIndex);	//returns the game decision according to a certain counting strategy (basic, hilo, etc...)				
			
					handMove(game, game.players[i], hand, decision, handIndex);
					
				}
				
			}
		    
	 	}

	}
	
	/**Description: Executes the player game decision according to the value of the variable decision
	 * @param game - game that is being played
	 * @param player - player that is going to execute the decision
	 * @param hand - hand where the action is going to be executed
	 * @param decision - game decision to be executed
	 * @param handIndex - index of the hand where the action is going to be executed
	 */
	private void handMove(Game game, Player player, Hand hand, char decision, int handIndex) {
				
		Card card1;

		if (decision=='h' ) {
			
			game.printString("player hits");
			
			card1=game.shoe.dealCard();
			//update the Hilo and Ace Five counts
		    game.info.updateHiloCount(card1, game.shoe.remainingDecks());
			game.info.uptadeCountAceFive(card1);
			
			
			if(player.hit(hand, card1)) { //if true it means it had a bust
				game.printString(printHandConsideringSplit(player.hasMadeSplit, hand, (handIndex+1)));
				game.printString(printActionConsideringSplit(player.hasMadeSplit, hand, (handIndex+1), "busts"));
			}else {
				game.printString(printHandConsideringSplit(player.hasMadeSplit, hand, (handIndex+1)));
			}
			
			   
		}else if (decision=='s'){
			
			game.printString(printActionConsideringSplit(player.hasMadeSplit, hand, (handIndex+1), "stands"));
			player.stand(hand);
			
		}else if (decision=='i'){
		
			game.printString("player is insuring");
			player.insurance();
		
		}else if (decision=='u'){
			
			game.printString("player is surrendering");
			player.surrender(hand);
		
		}else if (decision=='p'){
			
			game.printString("player is splitting");
				
			
			card1=game.shoe.dealCard();
			//update the Hilo and Ace Five counts
		    game.info.updateHiloCount(card1, game.shoe.remainingDecks());
			game.info.uptadeCountAceFive(card1);
			
		    			
			player.split(hand, card1);
			game.printString("playing "+ ordinal(handIndex+1) + " hand...");
			game.printString("player's hand ["+ (handIndex+1)+"] "+hand);
				
		
		}else if (decision=='2'){
		
			//game.printString("player is doubling down");
			card1=game.shoe.dealCard();
			
			 //update the Hilo and Ace Five counts
		    game.info.updateHiloCount(card1, game.shoe.remainingDecks());
			game.info.uptadeCountAceFive(card1);
			
			if(player.doubleDown(hand, card1)) {//if true it means it had a bust
				game.printString(printHandConsideringSplit(player.hasMadeSplit, hand, (handIndex+1)));
				game.printString(printActionConsideringSplit(player.hasMadeSplit, hand, (handIndex+1), "busts"));
				
			}else {
				game.printString(printHandConsideringSplit(player.hasMadeSplit, hand, (handIndex+1)));
			}
		}
	}
	
	/**Description: return a string with the hand of the player with the indication of the hand number if
	 * 				there is more than one hand (has made a split)
	 * @param hasMadeSplit - if true it means the player has already made a split
	 * @param hand - hand to be printed
	 * @param handIndex - index of the hand to be printed
	 * @return - a string with the player hand
	 */
	private String printHandConsideringSplit(boolean hasMadeSplit, Hand hand, int handIndex) {
		
		if(hasMadeSplit) {
			return("player's hand ["+ handIndex+"] "+hand);
		}else {
			return("player's hand "+hand);
		}
	
	}
	
	/**Description: return a string with the action the player has executed. It also has 
	 * 				an indication of the hand number if there is more than one hand (has made a split)
	 * 				
	 * @param hasMadeSplit - if true it means the player has already made a split
	 * @param hand - hand that has executed the action
	 * @param handIndex - index of the hand that has executed the action
	 * @param action - action the player has executed
	 * @return - a string with the player action
	 */
	private String printActionConsideringSplit(boolean hasMadeSplit, Hand hand, int handIndex, String action) {
		if(hasMadeSplit) {		
			return("player " + action+" ["+ handIndex+"] ");
		}else {
			return("player " + action);
		}
	}
	
	/**Description: changes the number from cardinal to ordinal
	 * 				i.e.  1 => 1st, 2 => 2nd, etc...
	 * @param i - cardinal number
	 * @return - ordinal number
	 */
	private String ordinal(int i) {
	    String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
	    switch (i % 100) {
	    case 11:
	    case 12:
	    case 13:
	        return i + "th";
	    default:
	        return i + suffixes[i % 10];

	    }
	}
	
}

