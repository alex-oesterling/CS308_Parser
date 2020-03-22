package slogo.exceptions;

/**
 * @author Tyler Meier
 */
public class InstantException extends SuperException {

    /**
     * Thrown when there is an instantiation exception
     * @param cause cause of error
     * @param text text to be displayed
     */
    public InstantException(Throwable cause, String text){
        super(cause, text);
    }
}
