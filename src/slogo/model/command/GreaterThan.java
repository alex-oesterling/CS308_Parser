package slogo.model.command;

import slogo.model.Turtle;
import java.util.List;

/**
 * @author Tyler Meier and Dana Mulligan
 */
public class GreaterThan extends Command {

  /**
   * GreaterThan constructor for checking a>b
   * Only works for ints, Integers, doubles, Doubles, floats, and Floats
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public GreaterThan(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
    super(doubleList.get(SECOND_INDEX)>doubleList.get(FIRST_INDEX));
  }

}
