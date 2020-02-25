package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class NaturalLog extends Command {

  /**
   * NaturalLog constructor that returns the natural log of a
   * performs ln(a)
   * @param a
   */
  public NaturalLog(double a){
    super(Math.log(a));
  }
}
