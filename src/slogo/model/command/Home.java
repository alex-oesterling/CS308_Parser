package slogo.model.command;

import slogo.model.Turtle;
import java.util.List;

/**
 * @author Tyler Meier and Dana Mulligan
 */
public class Home extends Command {

  private static final int DEFAULT_STARTING_X = 0;
  private static final int DEFAULT_STARTING_Y = 0;
  private Turtle t;

  /**
   * Constructor for the home command
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public Home(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super();
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Override super getResult();
   * this is done here instead of setting in constructor
   * in case the turtle moves and the same command object is executed again
   * @return distance to Home
   */
  @Override
  public Double getResult(){
    return t.distanceToPosition(DEFAULT_STARTING_X, DEFAULT_STARTING_Y);
  }

  /**
   * Allows home command to be executed and sends turtle back to starting position
   * @return the distance the turtle moved
   */
  @Override
  public Double execute() {
    t.goHome();
    return t.distanceToPosition(DEFAULT_STARTING_X, DEFAULT_STARTING_Y);
  }
}
