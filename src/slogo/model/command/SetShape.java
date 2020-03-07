package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class SetShape extends Command {

  private static final String UPDATE = "updateShape";
  private Double shape;
  private Turtle t;

  /**
   * Set shape constructor, takes in the index for the shape of which the current turtle should be
   * changed to
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public SetShape(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super();
    shape = doubleList.get(FIRST_INDEX);
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Gets the result/the index for the shape to be set to
   *
   * @return the shape index
   */
  @Override
  public Double getResult() {
    return t.getShape();
  }

  /**
   * Allows the set shape command to be executed
   *
   * @return calls the get result to return index of specific shape
   */
  @Override
  public Double execute() {
    t.setShape(shape);
    return this.getResult();
  }

  /**
   * Returns the string for method reflection
   * @return the update shape string
   */
  @Override
  public String getViewInteractionString() {
    return UPDATE;
  }
}
