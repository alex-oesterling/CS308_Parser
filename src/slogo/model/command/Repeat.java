package slogo.model.command;

import slogo.model.Turtle;

import java.util.ArrayList;
import java.util.List;

public class Repeat extends CommandWithReturningList{

  private Double times;
  private List<Command> commands;
  private List<Command> returningList;

  /**
   *
   * @param reps
   * @param commandList
   */
  public Repeat(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super();
    times = reps;
    //commands = commandList;
  }

  @Override
  /**
   * get result returns the last value of the last command to be executed
   * @return
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
