package slogo.exceptions;


public class NoMethodException extends SuperException {

    /**
     * Thrown when there is an error involving variables
     * @param cause cause of error
     * @param text text to be displayed
     */
    public NoMethodException(Throwable cause, String text){
        super(cause, text);
    }
}
