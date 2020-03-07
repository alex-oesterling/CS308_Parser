package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Power extends Command {

  /**
   * Power Constructor, passes up result to super
   * performs a^b
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public Power(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
    super(Math.pow(doubleList.get(SECOND_INDEX), doubleList.get(FIRST_INDEX)));
  }
}
