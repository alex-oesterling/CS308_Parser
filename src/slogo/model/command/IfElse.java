package slogo.model.command;

import java.util.ArrayList;
import java.util.List;

public class IfElse extends CommandWithReturningList {

  private boolean conditionResult;
  private List<Command> trueList, falseList, returningList;

  public IfElse(Double condition, List<Command> falseCommands, List<Command> trueCommands){
    super();
    falseList = falseCommands;
    trueList = trueCommands;
    conditionResult = (condition!=0.0);
  }

  @Override
  public Double getResult() {
    returningList = new ArrayList<>();
    if(conditionResult){
      returningList = trueList;
    } else {
      returningList = falseList;
    }

    return returningList.get(returningList.size()-1).getResult();
  }

  @Override
  public List<Command> getCommandList() {
    this.execute();
    return returningList;
  }
}
