package slogo.model.command;

import slogo.model.Turtle;
import java.util.List;

/**
 * @author Tyler Meier and Dana Mulligan
 */
public class Forward extends Command {

  private Turtle t;
  private Double distance;

  /**
   * Forward constructor, to get the value for going forward
   * Calls super
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public Forward(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super(doubleList.get(FIRST_INDEX));
    t = turtleList.get(FIRST_INDEX);
    distance = doubleList.get(FIRST_INDEX);
  }

  /**
   * Allows the turtle to move forward
   * @return distance travelled
   */
  @Override
  public Double execute(){
    t.move(distance);
    return distance;
  }

  /**
   * Get the distance to be returned
   * @return distance
   */
  @Override
  public Double getResult() {
    return distance;
  }

}

