package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Backward extends Command {

  private static final Double BACK = -1.0;
  private Double distance;
  private Turtle t;

  /**
   * Backward constructor, to get the value of going backward Calls super and sets the backward
   * value
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public Backward(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super(doubleList.get(FIRST_INDEX));
    t = turtleList.get(FIRST_INDEX);
    distance = doubleList.get(FIRST_INDEX);
  }

  /**
   * Allows the command backwards to be executed
   *
   * @return the distance traveled
   */
  @Override
  public Double execute() {
    t.move(distance * BACK);
    return distance;
  }

  /**
   * Get the distance to be returned
   *
   * @return distance
   */
  @Override
  public Double getResult() {
    return distance;
  }

}
