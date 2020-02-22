package slogo.model.command.mathcommand;

public class Tangent extends MathCommand {

  /**
   * Default Tangent constructor, calls super constructor
   * and sets the value to return as the tangent of the parameter
   * performs tan(a)
   * @param a value to take the tangent of
   */
  public Tangent(double a){
    super();
    super.sendMathResultUp(Math.tan(a));
  }
}
