package slogo.exceptions;


public class InvalidPropertyException extends RuntimeException {
    private String message;

    /**
     * Thrown when there is an error involving variables
     * @param cause cause of error
     * @param text text to be displayed
     */
    public InvalidPropertyException(Throwable cause, String text){
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
