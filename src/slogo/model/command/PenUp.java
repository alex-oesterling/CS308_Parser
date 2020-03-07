package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class PenUp extends Command {

  private static final double NOT_DRAWING = 0;
  private static final String UPDATE = "updatePenStatus";
  private Turtle t;

  /**
   * Constructor for pen up which allows the turtle to move without drawing, lifts pen sets the
   * drawing to false
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public PenUp(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super(NOT_DRAWING);
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Allows the command pen up to be executed
   *
   * @return 0 for false (not drawing)
   */
  @Override
  public Double execute() {
    t.setDrawing(NOT_DRAWING);
    return NOT_DRAWING;
  }

  /**
   * Returns the string for method reflection
   * @return the update pen status string
   */
  @Override
  public String getViewInteractionString() {
    return UPDATE;
  }
}