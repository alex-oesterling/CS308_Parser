package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class ClearScreen extends Command {

  private static final String UPDATE = "clear";
  private Turtle t;

  /**
   * The  clear screen constructor which erases the turtle's trails and sends it to its home
   * position
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public ClearScreen(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super();
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Override super getResult(); this is done here instead of setting in constructor in case the
   * turtle moves and the same command object is executed again
   *
   * @return distance to Home, the distance the turtle will travel when executed
   */
  @Override
  public Double getResult() {
    return t.distanceToPosition(Turtle.DEFAULT_STARTING_X, Turtle.DEFAULT_STARTING_Y);
  }

  /**
   * Allows the clear screen command to be executed, clears the screen and returns the distance the
   * turtle moved
   *
   * @return distance the turtle moved
   */
  @Override
  public Double execute() {
    t.goHome();
    return this.getResult();
  }

  /**
   * Returns the string for method reflection
   * @return the clear string
   */
  @Override
  public String getViewInteractionString() {
    return UPDATE;
  }
}
