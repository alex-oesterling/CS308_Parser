package slogo.exceptions;


public class IllegalException extends SuperException {

    /**
     * Thrown when there is an error involving variables
     * @param cause cause of error
     * @param text text to be displayed
     */
    public IllegalException(Throwable cause, String text){
        super(cause, text);
    }
}
