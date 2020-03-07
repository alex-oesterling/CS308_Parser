package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class HideTurtle extends Command {

  private static final double HIDDEN = 0;
  private static final String UPDATE = "updateTurtleView";
  private Turtle t;

  /**
   * Constructor for hide turtle which allows the turtle to be hidden by setting visibility to be
   * false Pass up the return value to the super constructor, which is 0
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public HideTurtle(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super(HIDDEN);
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Set the turtle's visibility to hidden, and return false showing that it cannot be seen
   *
   * @return 0
   */
  @Override
  public Double execute() {
    t.setVisibility(HIDDEN);
    return HIDDEN;
  }

  /**
   * Returns the string for method reflection
   * @return the update turtle view string
   */
  @Override
  public String getViewInteractionString() {
    return UPDATE;
  }
}

