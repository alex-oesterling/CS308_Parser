package slogo.model.command.turtlecommand.querycommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

public class XCoordinate extends Command {

  /**
   * Takes in a turtle, and returns the x coordinate of that turtle
   * @param body turtle
   */
  public XCoordinate(Turtle body){
    super(body.getX());
  }
}
