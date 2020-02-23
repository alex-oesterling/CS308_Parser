package slogo.model.command.mathcommand;

public class Difference extends MathCommand {

  /**
   * Default constructor that calls the super constructor
   * and sets the value to return as the difference between the two
   * provided values;
   * performs a-b
   * @param minuend a
   * @param subtrahend b
   */
  public Difference(double minuend, double subtrahend){
    super(minuend-subtrahend);
  }
}
