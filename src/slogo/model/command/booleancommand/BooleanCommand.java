package slogo.model.command.booleancommand;

import slogo.model.command.Command;

public class BooleanCommand extends Command{// implements Cloneable{

  private static final double TRUE = 1;
  private static final double FALSE_AND_DEFAULT = 0;
  private double result;

  /**
   * Boolean constructor, call super Command constructor
   */
  public BooleanCommand(){
    super();
    result = FALSE_AND_DEFAULT;
  }

  @Override
  public double getResult() {
    return result;
  }

  protected void changeBooleanResultToDouble(boolean condition) {
    if (condition) {
      result = TRUE;
    } else {
      result = FALSE_AND_DEFAULT;
    }
  }
}
