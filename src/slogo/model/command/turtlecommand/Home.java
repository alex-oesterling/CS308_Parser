package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Home extends TurtleCommand {

  /**
   * TurtleCommand constructor with an incoming Turtle
   *
   * @param body
   */
  public Home(Turtle body) {
    super(body);
    super.sendHome();
  }
}
