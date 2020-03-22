package slogo.exceptions;

/**
 * @author Tyler Meier
 */
public class InvalidConstantException extends SuperException {

    /**
     * Thrown when there is an error involving constants
     * @param cause cause of error
     * @param text text to be displayed
     */
    public InvalidConstantException(Throwable cause, String text){
        super(cause, text);
    }
}
