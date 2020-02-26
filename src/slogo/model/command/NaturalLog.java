package slogo.model.command;

public class NaturalLog extends Command {

  /**
   * NaturalLog constructor that returns the natural log of a
   * performs ln(a)
   * @param a
   */
  public NaturalLog(Double a){
    super(Math.log(a));
  }
}
