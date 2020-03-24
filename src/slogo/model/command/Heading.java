package slogo.model.command;

import slogo.model.Turtle;
import java.util.List;

/**
 * @author Tyler Meier and Dana Mulligan
 */
public class Heading extends Command {

  private Turtle t;

  /**
   * Heading constructor, gets the turtles current heading in degrees
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public Heading(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super();
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Return the current heading of the turtle this is separate from setting it in super in case this
   * command is called within a loop
   *
   * @return the turtle's heading
   */
  @Override
  public Double getResult() {
    return t.getHeading();
  }

  /**
   * Allows the heading command to be execute and return the turtle's heading in degrees
   *
   * @return turtle heading in degrees
   */
  @Override
  public Double execute() {
    return t.getHeading();
  }
}
