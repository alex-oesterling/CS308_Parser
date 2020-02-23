package slogo.model.command.booleancommand;

import slogo.model.command.Command;

public class BooleanCommand extends Command{
  /**
   * Boolean constructor, call super Command constructor
   */
  public BooleanCommand(boolean condition){
    super(condition?1.0:0.0); //convert a boolean to a double
  }
}
