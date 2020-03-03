package slogo.model.command;

import java.util.List;

public class Repeat extends Command{

  private static final Double DEFAULT = 0.0;
  private Double times;
  private List<Command> commands;

  /**
   *
   * @param reps
   * @param commandList
   */
  public Repeat(Double reps, List<Command> commandList){
    times = reps;
    commands = commandList;
  }

  @Override
  /**
   * get result returns the last value of the last command to be executed
   * @return
   */
  public Double getResult(){
    return commands.get(commands.size()-1).getResult();
  }

  @Override
  /**
   * executes the list of commands the specified number of times
   */
  public Double execute(){
    Double temp = DEFAULT;
    for(int k=0; k<times; k++) {
      for (Command c : commands) {
        temp = c.execute();
      }
    }
    return temp;
  }
}
