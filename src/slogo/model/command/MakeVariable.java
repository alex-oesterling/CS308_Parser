package slogo.model.command;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import slogo.model.Parser;
import slogo.model.Turtle;

public class MakeVariable extends Command {

  private List<String> variables;

  /**
   * MakeVariable constructor for creating a command that holds a double
   * assumes one double is coming in, and we only care about that
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public MakeVariable(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
    super();
    variables = stringList;
  }

//  private void dealWithMakingVariables(String line, Parser syntax){
//    List<String> copyLines = new CopyOnWriteArrayList(variables);
//    copyLines.remove(line);
//    String variable = copyLines.get(0);
//    String firstCommand = copyLines.get(1); //todo magic number
//    String type = syntax.getSymbol(firstCommand);
//    copyLines.remove(variable);
//    if (copyLines.size() > 1 || (copyLines.size() == 1 && type.equals("Command"))){
//      control.addUserCreatedCommand(variable, copyLines);
//    } else if (copyLines.size() == 1 && type.equals("Constant")){
//      control.addUserCreatedVariable(variable, firstCommand);
//    }
//  }
}
