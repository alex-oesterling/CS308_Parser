package slogo.exceptions;


public class InvalidPropertyException extends SuperException {

    /**
     * Thrown when there is an error involving variables
     * @param cause cause of error
     * @param text text to be displayed
     */
    public InvalidPropertyException(Throwable cause, String text){
        super(cause, text);
    }
}
