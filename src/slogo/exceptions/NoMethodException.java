package slogo.exceptions;

/**
 * @author Tyler Meier
 */
public class NoMethodException extends SuperException {

    /**
     * Thrown when there is a no method found exception
     * @param cause cause of error
     * @param text text to be displayed
     */
    public NoMethodException(Throwable cause, String text){
        super(cause, text);
    }
}
