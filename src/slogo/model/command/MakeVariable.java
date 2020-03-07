package slogo.model.command;

import java.util.List;
import slogo.model.Turtle;

public class MakeVariable extends Command {

  private static final String USER_CONSTANT = "addUserConstantToMap";
  private static final String USER_COMMAND = "addUserCommandToMap";
  private static final String SEPARATION = " ";
  private static boolean isConstant = false;
  private double constant;
  private List<Command> command;
  private String variable;


  /**
   * MakeVariable constructor for creating a command that holds a double
   * assumes one double is coming in, and we only care about that
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public MakeVariable(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
    super();
    variable = stringList.get(FIRST_INDEX);
    if (! commandList.isEmpty()){
      command = commandList.get(FIRST_INDEX);
    } else if (! doubleList.isEmpty()){
      constant = doubleList.get(FIRST_INDEX);
      isConstant = true;
    }
  }

  /**
   * Returns the constant if the variable is a constant variable
   * @return constant
   */
  @Override
  public Double getResult() {
    if(isConstant) {
      return constant;
    }
    return command.get(FIRST_INDEX).getResult();
  }

  /**
   * Returns the command expression  if the variable is a command variable
   * @return command list
   */
  @Override
  public List<Command> getCommandList() {
    if(isConstant){
      return super.getCommandList();
    }
    return command;
  }

  /**
   * Returns the string for method reflection
   * @return the string to update map
   */
  @Override
  public String getViewInteractionString() {
    if (isConstant){
      isConstant = false;
      return USER_CONSTANT + SEPARATION + variable;
    } else {
      return USER_COMMAND + SEPARATION + variable;
    }
  }
}
