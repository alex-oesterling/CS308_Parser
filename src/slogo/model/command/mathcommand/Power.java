package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class Power extends Command {

  /**
   * Power Constructor, passes up result to super
   * performs a^b
   * @param base a
   * @param exponent b
   */
  public Power(double base, double exponent){
    super(Math.pow(base, exponent));
  }
}
