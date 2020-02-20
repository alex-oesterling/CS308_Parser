package slogo.model.command;

abstract public class Command {
  //TODO make error catching in each of the command classes, so that if the wrong parameters are passed in the code doesn't break

  //private static final int DEFAULT_RESULT_VALUE = 50;
  //private double result;

  /**
   * Constructor, initialize result to the default value
   */
  public Command(){
    //result = DEFAULT_RESULT_VALUE;
  }

  /**
   * getter for result, what the command "returns"
   * @return result
   */
  abstract public double getResult();

  /**
   * change the value of result
   * @param value what result will be set to
   */
  //protected void setResult(double value){
    //result = value;
  //}

  //TODO add throwCommandParametersError()
}
