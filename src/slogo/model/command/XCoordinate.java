package slogo.model.command;

import slogo.model.Turtle;

public class XCoordinate extends Command {

  private Turtle t;
  /**
   * Takes in a turtle, and returns the x coordinate of that turtle
   * @param body turtle
   */
  public XCoordinate(Turtle body){
    super();
    t = body;
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
