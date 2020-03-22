package slogo.exceptions;

/**
 * Super class for all of the exception classes
 * @author Tyler Meier
 */
abstract public class SuperException extends RuntimeException{

    private String message;

    /**
     * Allows the exception to be thrown, default method
     * @param cause cause of error
     * @param text text to be displayed
     */
    public SuperException(Throwable cause, String text){
        super(cause);
        message = text;
    }

    /**
     * Gets the error message
     * @return the message to be displayed
     */
    public String getMessage(){
        return message;
    }
}
