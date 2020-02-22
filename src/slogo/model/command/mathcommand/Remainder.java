package slogo.model.command.mathcommand;

public class Remainder extends MathCommand{

  /**
   * Default constructor for finding the remainder of a division
   * calls the super constructor, and sets the value to return
   * @param dividend
   * @param divisor
   */
  public Remainder(double dividend, double divisor){
    super();
    super.sendMathResultUp(dividend%divisor);
  }
}
