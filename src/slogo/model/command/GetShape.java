package slogo.model.command;

import slogo.model.Turtle;
import java.util.List;

/**
 * @author Tyler Meier and Dana Mulligan
 */
public class GetShape extends Command {

  private Turtle t;

  /**
   * Get shape constructor, returns the current index of the shape of the turtle
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public GetShape(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super();
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Return the current shape of the turtle
   *
   * @return the turtle's shape/index
   */
  @Override
  public Double getResult() {
    return t.getShape();
  }

  /**
   * Allows the get shape command to be executed
   *
   * @return call for the get result to return index/shape
   */
  @Override
  public Double execute() {
    return this.getResult();
  }
}
