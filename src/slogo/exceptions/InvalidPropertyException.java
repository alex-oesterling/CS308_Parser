package slogo.exceptions;

/**
 * @author Tyler Meier
 */
public class InvalidPropertyException extends SuperException {

    /**
     * Thrown when there is an error involving an invalid properties file
     * @param cause cause of error
     * @param text text to be displayed
     */
    public InvalidPropertyException(Throwable cause, String text){
        super(cause, text);
    }
}
