package slogo.exceptions;

abstract public class SuperException extends RuntimeException{

    private String message;

    /**
     *
     * @param cause
     * @param text
     */
    public SuperException(Throwable cause, String text){
        super(cause);
        message = text;
    }

    /**
     *
     * @return
     */
    public String getMessage(){
        return message;
    }
}
