package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class NaturalLog extends Command {

  /**
   * NaturalLog constructor that returns the natural log of a
   * performs ln(value given)
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public NaturalLog(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
    super(Math.log(doubleList.get(FIRST_INDEX)));
  }
}
