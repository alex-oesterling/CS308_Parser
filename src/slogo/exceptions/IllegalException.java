package slogo.exceptions;

/**
 * @author Tyler Meier
 */
public class IllegalException extends SuperException {

    /**
     * Thrown when there is an illegal access exception
     * @param cause cause of error
     * @param text text to be displayed
     */
    public IllegalException(Throwable cause, String text){
        super(cause, text);
    }
}
