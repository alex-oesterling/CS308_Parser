package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Forward {//extends TurtleCommand {

  private double increment;

  public Forward(double pos) {
    //super(new Turtle());
    increment = pos;
  }

}
