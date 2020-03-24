package slogo.exceptions;


public class NoClassException extends SuperException {

    /**
     * Thrown when there is an error involving variables
     * @param cause cause of error
     * @param text text to be displayed
     */
    public NoClassException(Throwable cause, String text){
        super(cause, text);
    }
}
