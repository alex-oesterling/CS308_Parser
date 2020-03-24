package slogo.exceptions;

/**
 * @author Tyler Meier
 */
public class NoClassException extends SuperException {

    /**
     * Thrown when there is a no class found exception
     * @param cause cause of error
     * @param text text to be displayed
     */
    public NoClassException(Throwable cause, String text){
        super(cause, text);
    }
}
