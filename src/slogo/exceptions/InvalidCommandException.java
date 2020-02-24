package slogo.exceptions;

public class InvalidCommandException extends RuntimeException {
  public InvalidCommandException(Throwable cause) {
    super(cause);
  }

  public InvalidCommandException(Throwable cause, String message) {
    super(message, cause);
  }
}
