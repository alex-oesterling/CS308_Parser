package slogo.model.command.turtlecommand.querycommand;

import slogo.model.Turtle;

public class XCoordinate extends QueryCommand {

  /**
   * Takes in a turtle, and returns the x coordinate of that turtle
   * @param body turtle
   */
  public XCoordinate(Turtle body){
    super(body.getX());
  }
}
