package slogo.model.command;

import slogo.model.Turtle;

import java.util.ArrayList;
import java.util.List;

public class IfElse extends CommandWithReturningList {

  private boolean conditionResult;
  private List<Command> trueList, falseList, returningList;

  public IfElse(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super();
    falseList = commandList.get(FIRST_INDEX);
    trueList = commandList.get(SECOND_INDEX);
    conditionResult = (doubleList.get(FIRST_INDEX)!=0.0);
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
