package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class Remainder extends Command {

  /**
   * Default constructor for finding the remainder of a division
   * calls the super constructor, and sets the value to return
   * @param dividend
   * @param divisor
   */
  public Remainder(double dividend, double divisor){
    super(dividend%divisor);
  }
}
