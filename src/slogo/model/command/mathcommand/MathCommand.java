package slogo.model.command.mathcommand;

import slogo.model.command.Command;

public class MathCommand extends Command {

  private static final double MATH_DEFAULT_VALUE = 1;
  private double result;

  public MathCommand(){
    super();
    result = MATH_DEFAULT_VALUE;
  }

  @Override
  public double getResult() {
    return result;
  }

  protected void sendMathResultUp(double value){
    result = value;
  }
}
