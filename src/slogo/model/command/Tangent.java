package slogo.model.command;

public class Tangent extends Command {

  /**
   * Default Tangent constructor, calls super constructor
   * and sets the value to return as the tangent of the parameter
   * performs tan(a)
   * @param a value to take the tangent of
   */
  public Tangent(Double a){
    super(Math.tan(a));
  }
}
