package slogo.model.command.booleancommand;

import slogo.model.command.Command;

abstract public class BooleanCommand extends Command{// implements Cloneable{

  private static final double TRUE = 1;
  private static final double FALSE = 0;

  /**
   * Boolean constructor, call super Command constructor
   */
  public BooleanCommand(){
    super();
  }

  protected void changeBooleanResultToDouble(boolean condition) {
    if (condition) {
      super.setResult(TRUE);
    } else {
      super.setResult(FALSE);
    }
  }
}
