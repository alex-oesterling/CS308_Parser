package slogo.exceptions;


public class InvalidConstantException extends SuperException {
    private String message;

    /**
     * Thrown when there is an error involving constants
     * @param cause cause of error
     * @param text text to be displayed
     */
    public InvalidConstantException(Throwable cause, String text){
        super(cause, text);
    }
}
