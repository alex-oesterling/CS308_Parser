package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Home extends TurtleCommand {

  public Home(Turtle body) {
    super();
    body.goHome();
  }
}
