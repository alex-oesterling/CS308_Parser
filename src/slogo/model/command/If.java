package slogo.model.command;

import slogo.model.Turtle;

import java.util.ArrayList;
import java.util.List;

public class If extends Command {

  private boolean conditionResult;
  private List<Command> commands, returningList;
  private static final Double DEFAULT = 0.0;

  public If(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
    super();
    commands = commandList.get(FIRST_INDEX);
    conditionResult = (doubleList.get(FIRST_INDEX)!=0.0);
  }

  @Override
  public Double getResult() {
    returningList = new ArrayList<>();
    if(conditionResult){
      returningList = commands;
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
