package slogo.exceptions;

public class InvalidVariableException extends RuntimeException {
    public InvalidVariableException(Throwable cause) {
        super(cause);
    }

    public InvalidVariableException(String message, Throwable cause){
        super(message, cause);
    }
}
