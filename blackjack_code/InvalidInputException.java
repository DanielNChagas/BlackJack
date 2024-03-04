package blackjack;

/**
 * Exception used when an invalid input is introduced by the user
 */
public class InvalidInputException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates an Invalid Input Exception, using an error Message
	 */
	public InvalidInputException(String errorMessage) {
	        super(errorMessage);
	}
}
