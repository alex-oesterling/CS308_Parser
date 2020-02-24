package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class Minus extends Command {

  /**
   * Default Minus constructor, calls super constructor
   * sets the value to return as -a
   * @param value a
   */
  public Minus(double value){
    super(-1*value);
  }
}
