package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Forward extends TurtleCommand {

  public Forward(Turtle body, double pos) {
    super(pos);
    body.move(pos);
  }
}
