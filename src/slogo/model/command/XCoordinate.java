package slogo.model.command;

import slogo.model.Turtle;

public class XCoordinate extends Command {

  /**
   * Takes in a turtle, and returns the x coordinate of that turtle
   * @param body turtle
   */
  public XCoordinate(Turtle body){
    super(body.getX());
  }
}
