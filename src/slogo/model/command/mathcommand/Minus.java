package slogo.model.command.mathcommand;

public class Minus extends MathCommand {

  /**
   * Default Minus constructor, calls super constructor
   * sets the value to return as -a
   * @param value a
   */
  public Minus(double value){
    super(-1*value);
  }
}
