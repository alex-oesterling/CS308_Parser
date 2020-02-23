package slogo.model.command;

abstract public class Command {
  //TODO make error catching in each of the command classes, so that if the wrong parameters are passed in the code doesn't break

  private static final double DEFAULT_COMMAND_RESULT = 0;
  private double result;

  /**
   * Command constructor, sets result to return value
   * @param returnValue value returned by each specific command
   */
  public Command(double returnValue){
    result = returnValue;
  }

  /**
   * Default command constructor
   */
  public Command(){
    this(DEFAULT_COMMAND_RESULT);
  }

  /**
   * getter for result, what the command "returns"
   * @return result
   */
  public double getResult(){
    return result;
  }

  //TODO add throwCommandParametersError()
}
