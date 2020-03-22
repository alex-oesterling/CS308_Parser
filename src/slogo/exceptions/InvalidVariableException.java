package slogo.exceptions;

/**
 * @author Tyler Meier
 */
public class InvalidVariableException extends SuperException {

    /**
     * Thrown when there is an error involving variables
     * @param cause cause of error
     * @param text text to be displayed
     */
    public InvalidVariableException(Throwable cause, String text){
        super(cause, text);
    }
}
