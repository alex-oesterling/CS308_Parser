package slogo.model.command;

import slogo.model.Turtle;
import java.util.List;

/**
 * @author Tyler Meier and Dana Mulligan
 */
public class YCoordinate extends Command {

  private Turtle t;

  /**
   * Takes in a turtle, and returns the y coordinate of that turtle
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public YCoordinate(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super(turtleList.get(FIRST_INDEX).getY());
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Allows the yCoordinate command to be executed
   *
   * @return the y coordinate of the turtle
   */
  @Override
  public Double execute() {
    return t.getY();
  }
}
