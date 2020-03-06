package slogo.exceptions;


public class InvalidConstantException extends RuntimeException {
    private String message;

    /**
     * Thrown when there is an error involving constants
     * @param cause cause of error
     * @param text text to be displayed
     */
    public InvalidConstantException(Throwable cause, String text){
        super(cause);
        message = text;
    }

    /**
     * Gets the message for the error
     * @return the message
     */
    public String getMessage(){
        return message;
    }
}
