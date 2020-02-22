package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Forward extends TurtleCommand {

  private double increment;

  public Forward(Turtle body, double pos) {
    super(body, pos);
    increment = pos;
  }
}
