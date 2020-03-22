package slogo.exceptions;

/**
 * @author Tyler Meier
 */
public class InvocationException extends SuperException {

    /**
     * Thrown when there is an invocation target exception
     * @param cause cause of error
     * @param text text to be displayed
     */
    public InvocationException(Throwable cause, String text){
        super(cause, text);
    }
}
