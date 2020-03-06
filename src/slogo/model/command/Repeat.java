package slogo.model.command;

import slogo.model.Turtle;

import java.util.ArrayList;
import java.util.List;

public class Repeat extends CommandWithReturningList{

  private Double times;
  private List<Command> commands;
  private List<Command> returningList;

  /**
   * Repeat constructor, take sin double and list of commands
   * and multiplies list of commands by the given double
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   */
  public Repeat(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super();
    times = doubleList.get(FIRST_INDEX);
    commands = commandList.get(FIRST_INDEX);
  }

  @Override
  /**
   * Get result returns the last value of the last command to be executed
   * @return expanded list of commands to be done
   */
  public Double getResult(){
    List<Command> expandedList = getCommandList();
    return expandedList.get(expandedList.size()-1).getResult();
  }

  /**
   * return the commands the number of times asked for
   * @return commands * times in one list
   */
  @Override
  public List<Command> getCommandList() {
    returningList = new ArrayList<>();
    for(int k=0; k<times; k++) {
      for (Command c : commands) {
        returningList.addAll(c.getCommandList());
      }
    }
    return returningList;
  }
}
