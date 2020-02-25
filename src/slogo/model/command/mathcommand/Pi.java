package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class Pi extends Command {

  /**
   * Default constructor, calls super constructor
   * and sets the result to be returned as pi
   */
  public Pi(){
    super(Math.PI);
  }
}
