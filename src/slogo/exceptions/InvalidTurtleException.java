package slogo.exceptions;

public class InvalidTurtleException extends RuntimeException{
  public InvalidTurtleException(Throwable cause) {
    super(cause);
  }

  public InvalidTurtleException(String message, Throwable cause){
    super(message, cause);
  }
}
