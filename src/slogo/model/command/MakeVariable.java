package slogo.model.command;

import java.util.List;
import slogo.model.Turtle;

/**
 * @author Tyler Meier and Dana Mulligan
 */
public class MakeVariable extends Command {

  private static final String USER_CONSTANT = "addUserConstantToMap";
  private static final String USER_COMMAND = "addUserCommandToMap";
  private static final String SEPARATION = " ";
  private boolean isConstant;
  private double constant, result;
  private List<Command> command, returningList;
  private String variable, returningString;


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
      returningList = super.getCommandList();
      returningList.addAll(commandList.get(FIRST_INDEX));
      result = returningList.get(returningList.size()-1).getResult();
      returningString = USER_COMMAND + SEPARATION + variable;
    } else if (! doubleList.isEmpty()){
      result = doubleList.get(FIRST_INDEX);
      returningList = super.getCommandList();
      returningString = USER_CONSTANT + SEPARATION + variable;
    }
  }

  /**
   * Returns the constant if the variable is a constant variable
   * @return constant
   */
  @Override
  public Double getResult() {
    return result;
  }

  /**
   * Returns the command expression  if the variable is a command variable
   * @return command list
   */
  @Override
  public List<Command> getCommandList() {
    return returningList;
  }

  /**
   * Returns the string for method reflection
   * @return the string to update map
   */
  @Override
  public String getViewInteractionString() {
   return returningString;
  }

}
