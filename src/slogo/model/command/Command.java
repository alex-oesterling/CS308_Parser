package slogo.model.command;

abstract public class Command {

  private static final int DEFAULT_RESULT_VALUE = 0;
  private double result;

  /**
   * Constructor, initialize result to the default value
   */
  public Command(){
    result = DEFAULT_RESULT_VALUE;
  }

  /**
   * getter for result, what the command "returns"
   * @return result
   */
  public double getResult(){
    return result;
  }

  /**
   * change the value of result
   * @param value what result will be set to
   */
  protected void setResult(double value){
    result = value;
  }
}
