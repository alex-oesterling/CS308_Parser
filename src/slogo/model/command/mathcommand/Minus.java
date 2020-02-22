package slogo.model.command.mathcommand;

public class Minus extends MathCommand {

  /**
   * Default Minus constructor, calls super constructor
   * sets the value to return as a-b
   * @param minuend a
   * @param subtrahend b
   */
  public Minus(double minuend, double subtrahend){
    super();
    super.sendMathResultUp(minuend-subtrahend);
  }
}
