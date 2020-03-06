package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Or extends Command {

  /**
   * Or constructors, checks for value of first value||second value,
   * call super constructor and update result
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   */
  public Or(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(doubleList.get(FIRST_INDEX)!=0.0||doubleList.get(SECOND_INDEX)!=0.0);
  }

}
