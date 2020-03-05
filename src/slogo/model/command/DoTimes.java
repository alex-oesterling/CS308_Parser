package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class DoTimes{

  private Double times;
  private List<Command> commands;

  /**
   *
   * @param reps
   * @param commandList
   */
  public DoTimes(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    times = reps;
    //commands = commandList;
  }

  /**
   * get result returns the last value of the last command to be executed
   * @return
   */
  public Double getResult(){
    return commands.get(commands.size()-1).getResult();
  }

  /**
   * executes the list of commands the specified number of times
   */
  public void execute(){
    for(int k=0; k<times; k++) {
      for (Command c : commands) {
        c.execute();
      }
    }
  }
}
