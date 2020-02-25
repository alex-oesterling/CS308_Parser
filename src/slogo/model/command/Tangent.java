package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class Tangent extends Command {

  /**
   * Default Tangent constructor, calls super constructor
   * and sets the value to return as the tangent of the parameter
   * performs tan(a)
   * @param a value to take the tangent of
   */
  public Tangent(double a){
    super(Math.tan(a));
  }
}
