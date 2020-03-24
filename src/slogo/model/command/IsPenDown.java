package slogo.model.command;

import slogo.model.Turtle;
import java.util.List;

/**
 * @author Tyler Meier and Dana Mulligan
 */
public class IsPenDown extends Command {

  private Turtle t;

  /**
   * Return 1 if the pen is down, 0 otherwise
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public IsPenDown(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super();
    t = turtleList.get(0);
  }

  /**
   * Return if the pen is down on a turtle or not this is separate from setting it in the
   * constructor in case a specific IsPenDown object's execute() is called many times; i.e. object
   * is in a loop
   *
   * @return drawing status of the turtle; 1 if drawing or 0 if not drawing
   */
  @Override
  public Double getResult() {
    return t.getDrawingStatus();
  }
}
