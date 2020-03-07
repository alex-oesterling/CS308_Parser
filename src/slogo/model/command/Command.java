package slogo.model.command;

import java.util.ArrayList;
import java.util.List;

abstract public class Command {

  protected static final int FIRST_INDEX = 0;
  protected static final int SECOND_INDEX = 1;
  protected static final int THIRD_INDEX = 2;
  protected static final int FOURTH_INDEX = 3;
  private static final double DEFAULT_COMMAND_RESULT = 0;
  private static final String UPDATE = "update";
  private double result;

  /**
   * Command constructor, sets result to return value
   * @param returnValue value returned by each specific command
   */
  public Command(Double returnValue){ result = returnValue; }

  /**
   * Command constructor that takes a boolean and converts
   * it to a double, which is then set to the return value
   * @param condition incoming boolean to change to a double value
   */
  public Command(boolean condition){ this(condition?1.0:0.0); } //convert a boolean to a double

  /**
   * Default command constructor
   */
  public Command(){ this(DEFAULT_COMMAND_RESULT); }

  /**
   * Allows certain commands to be executed and returns the result
   * @return result
   */
  public Double getResult(){
    return result;
  }

  /**
   * Allows the string to be returned to allow for method reflection
   * @return the string name
   */
  public String getViewInteractionString(){
    return UPDATE;
  }

  /**
   * Make a list with just this command in it
   * @return the command list of just this command
   */
  public List<Command> getCommandList(){
    List<Command> ret = new ArrayList<>();
    ret.add(this);
    return ret;
  }

  /**
   * Executes the command
   * @return the double result
   */
  public Double execute(){
    return getResult();
  }
}
