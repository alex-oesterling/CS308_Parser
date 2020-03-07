package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class ShowTurtle extends Command {

  private static final double SHOWING = 1;
  private Turtle t;

  /**
   * Constructor for show turtle which allows the turtle to be seen
   *
   * @param turtleList  the list of turtles being brought in to use this command (if needed)
   * @param doubleList  the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   * @param stringList  the list of strings being used for this command (if needed)
   */
  public ShowTurtle(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList) {
    super(SHOWING);
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Setting turtle visibility to be true, and return true (1) for showing
   *
   * @return 1
   */
  @Override
  public Double execute() {
    t.setVisibility(SHOWING);
    return SHOWING;
  }
}