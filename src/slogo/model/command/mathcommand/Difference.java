package slogo.model.command.mathcommand;

public class Difference extends MathCommand {

  /**
   * Default constructor that calls the super constructor
   * and sets the value to return as the difference between the two
   * provided values;
   * performs a-b
   * @param valueToSubtractFrom a
   * @param valueToSubtract b
   */
  public Difference(double valueToSubtractFrom, double valueToSubtract){
    super();
    super.sendMathResultUp(Math.abs(valueToSubtractFrom-valueToSubtract));
  }
}
