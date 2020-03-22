package slogo.model.command;

import slogo.model.Turtle;
import java.util.List;

/**
 * @author Tyler Meier and Dana Mulligan
 */
public class Quotient extends Command {

  /**
   * Calls the super constructor, and sets the result to be returned
   * as the quotient of the two provided numbers;
   * performs a/b
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList the list of strings being used for this command (if needed)
   */
  public Quotient(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
    super(doubleList.get(SECOND_INDEX)/doubleList.get(FIRST_INDEX));
  }
}
