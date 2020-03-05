package slogo.model.command;

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
  public Repeat(Double reps, List<Command> commandList, String help){
    super();
    System.out.println(help);
    times = reps;
    commands = commandList;
  }

  @Override
  /**
   * get result returns the last value of the last command to be executed
   * @return
   */
  public Double getResult(){
    return 0.0;
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
        System.out.println("C: "+c);
        returningList.addAll(c.getCommandList());
      }
    }
    return returningList;
  }
}
