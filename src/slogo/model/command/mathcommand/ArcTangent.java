package slogo.model.command.mathcommand;

public class ArcTangent extends MathCommand {

  /**
   * Default constructor for ArcTangent
   * calls super constructor and then sets the re
   * @param a
   */
  public ArcTangent(double a){
    super(Math.atan(a));
  }
}
