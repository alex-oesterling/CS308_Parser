package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class XCoordinate extends Command {

  private Turtle t;
  /**
   * Takes in a turtle, and returns the x coordinate of that turtle
   * @param turtleList the list of turtles being brought in to use this command (if needed)
   * @param doubleList the list of doubles to be used for this command (if needed)
   * @param commandList the list of commands being used for this command (if needed)
   */
  public XCoordinate(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
    super(turtleList.get(FIRST_INDEX).getX());
    t = turtleList.get(FIRST_INDEX);
  }

  /**
   * Allows the xCoordinate command to be executed
   * @return the x coordinate of the turtle
   */
  @Override
  public Double execute() {
    return t.getX();
  }
}
