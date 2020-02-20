package slogo.model.command.mathcommand;

public class Sine extends MathCommand{

  /**
   * Default Sine constructor, calls super constuctor
   * and sets the value to return as the sine of the parameter;
   * performs sin(a)
   * @param a value to take the sine of
   */
  public Sine(double a){
    super();
    super.sendMathResultUp(Math.sin(a));
  }
}
