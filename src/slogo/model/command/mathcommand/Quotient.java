package slogo.model.command.mathcommand;

public class Quotient extends MathCommand {

  /**
   * Calls the super constructor, and sets the result to be returned
   * as the quotient of the two provided numbers;
   * performs a/b
   * @param dividend number to be divided; a
   * @param divisor number to divide by; b
   */
  public Quotient(double dividend, double divisor){
    super();
    super.sendMathResultUp(dividend/divisor);
  }
}
