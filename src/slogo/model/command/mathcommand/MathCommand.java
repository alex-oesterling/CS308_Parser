package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class MathCommand extends Command {

  private static final double MATH_DEFAULT_VALUE = 1;

  public MathCommand(double returnValue) {
    super(returnValue);
  }

  public MathCommand() {
    super(MATH_DEFAULT_VALUE);
  }

}
