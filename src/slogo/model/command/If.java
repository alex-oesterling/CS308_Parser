package slogo.model.command;

import java.util.ArrayList;
import java.util.List;

public class If extends CommandWithReturningList {

  private boolean conditionResult;
  private List<Command> commandList, returningList;

  public If(List<Command> commands, List<Command> conditions){
    super();
    commandList = commands;
    conditionResult = (conditions.get(conditions.size()-1).getResult()!=0.0);
  }

  @Override
  public Double getResult() {
    returningList = new ArrayList<>();
    if(conditionResult){
      returningList = commandList;
      return returningList.get(returningList.size()-1).getResult();
    }
    return DEFAULT;
  }

  @Override
  public List<Command> getCommandList() {
    this.execute();
    return returningList;
  }
}
