package slogo.model.command;

import java.util.ArrayList;
import slogo.model.Turtle;

import java.util.List;

public class DoTimes extends CommandWithReturningList{

  private Double times;
  private List<Command> commands, returningList;

  /**
   *
   * @param //reps
   * @param commandList
   */
  public DoTimes(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    times = doubleList.get(FIRST_INDEX);
    commands = commandList.get(FIRST_INDEX);
  }

  /**
   * get result returns the last value of the last command to be executed
   * @return
   */
  public Double getResult(){
    return commands.get(commands.size()-1).getResult();
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
