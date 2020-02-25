package slogo.exceptions;

public class InvalidCommandException extends RuntimeException {
  private String syntaxType;
  private String mySyntax;

  public InvalidCommandException(Throwable cause) {
    super(cause);
  }

  public InvalidCommandException(Throwable cause, String type, String syntax) {
    super(cause);
    syntaxType = type;
    mySyntax = syntax;
  }

  public String getSyntax(){return mySyntax;}

  public String getType(){return syntaxType;}
}
