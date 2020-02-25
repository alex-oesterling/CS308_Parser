package slogo.model.command;

public class ArcTangent extends Command {

  /**
   * Default constructor for ArcTangent
   * calls super constructor and then sets the re
   * @param a
   */
  public ArcTangent(Double a){
    super(Math.atan(a));
  }
}
