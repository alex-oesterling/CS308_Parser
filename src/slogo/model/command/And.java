package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class And extends Command {

  /**
   * Constructor of 'and' logic
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public And(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
   super(doubleList.get(FIRST_INDEX)!=0.0 && doubleList.get(SECOND_INDEX)!=0.0); //'convert' to booleans
  }

}
