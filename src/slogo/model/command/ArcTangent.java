package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class ArcTangent extends Command {

  /**
   * Default constructor for ArcTangent
   * calls super constructor and then sets the re
   * @param a
   */
  public ArcTangent(double a){
    super(Math.atan(a));
  }
}
