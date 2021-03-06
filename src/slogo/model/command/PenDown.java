package slogo.model.command;

import slogo.model.Turtle;
import java.util.List;

/**
 * @author Tyler Meier and Dana Mulligan
 */
public class PenDown extends Command {

  private static final double DRAWING = 1;
  private static final String UPDATE = "updatePenStatus";
  private Turtle t;

  /**
   * Constructor for pen down which allows the turtle to  start drawing/use its pen to draw sets the
   * drawing to true
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public PenDown(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super(DRAWING);
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Allows the penDown command to be executed
   *
   * @return 1 as true, meaning the pen is down
   */
  @Override
  public Double execute() {
    t.setDrawing(DRAWING);
    return DRAWING;
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