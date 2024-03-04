package main;

import blackjack.Game;

public class Main {
	
	public static void main(String[] args){
		
		if(args.length < 6 ) {	//Check if the minimum arguments where given has input of the program
			displayError();
		}
		
		String mode=args[0];
		String minBet=args[1];
		String maxBet=args[2];
		String balance=args[3];
		Game game1;
		//check if minBet,maxBet and balance arguments 	respect the conditions
		try { 
			if(Integer.parseInt(minBet) < 1 || Integer.parseInt(maxBet) < Integer.parseInt(minBet) * 10 || Integer.parseInt(maxBet) > Integer.parseInt(minBet) * 20 || Integer.parseInt(balance) < Integer.parseInt(minBet) * 50) {
				displayError();
			}
		}
		catch(NumberFormatException e){
			displayError();
		}
		
		if(mode.equals("-i")) {	//interactive mode
			if(args.length == 6 ) {	// checks if the number of arguments for this mode is correct
				String shoe=args[4];
				String shuffle=args[5];
				try { //checks if the shoes is between 4 and 8 and if the shuffle is between 10 and 90
					if(Integer.parseInt(shoe) < 4 || Integer.parseInt(shoe) > 8 || Integer.parseInt(shuffle) > 90 || Integer.parseInt(shuffle) < 10) {
						displayError();
					}
				}
				catch(NumberFormatException e){
					displayError();
				}

				game1= new Game(Integer.parseInt(shoe), Integer.parseInt(shuffle)); 
				game1.createPlayer(Float.parseFloat(balance), Integer.parseInt(minBet), Integer.parseInt(maxBet));
				game1.runGame();
			}else
				displayError();
		}else if(mode.equals("-s")) {	//simulation mode
			if(args.length == 8 ) {	// checks if the number of arguments for this mode is correct
				String shoe=args[4];
				String shuffle=args[5];
				String numOfShuffles=args[6];
				String strategy=args[7];
				try { //checks if the shoes is between 4 and 8 and if the shuffle is between 10 and 90
					Integer.parseInt(numOfShuffles);
					if(Integer.parseInt(shoe) < 4 || Integer.parseInt(shoe) > 8 || Integer.parseInt(shuffle) > 90 || Integer.parseInt(shuffle) < 10) {
						displayError();
					}
					if(!(strategy.equals("BS") || strategy.equals("BS-AF") || strategy.equals("HL") || strategy.equals("HL-AF")))
						displayError();
				}
				catch(NumberFormatException e){
					displayError();
				}


				game1 = new Game(Integer.parseInt(shoe), Integer.parseInt(shuffle), Integer.parseInt(numOfShuffles));
				game1.createPlayer(Float.parseFloat(balance), Integer.parseInt(minBet), Integer.parseInt(maxBet), strategy);
				game1.runGame();

			}else
				displayError();
		}else if(mode.equals("-d")) {
			if(args.length == 6 ) {	// checks if the number of arguments for this mode is correct
				String shoeFile=args[4]; 
				String cmdFile=args[5];
				
				game1 = new Game(shoeFile);
				game1.createPlayer(cmdFile, Float.parseFloat(balance), Integer.parseInt(minBet), Integer.parseInt(maxBet));
				game1.runGame();
			}else
				displayError();
		}else {
			displayError();
		}
		

		
	}
	
	private static void  displayError() {
		System.out.print("Error: Incorrect Arguments!");
		System.exit(-1);
	}
	
	
}
