package slogo.model.command.mathcommand;

public class Cosine extends MathCommand {

  /**
   * Default Cosine constructor, calls super constructor
   * sets value to return as the cosine of the parameter
   *
   * @param a
   */
  public Cosine(double a){
    super(Math.cos(a));
  }
}
