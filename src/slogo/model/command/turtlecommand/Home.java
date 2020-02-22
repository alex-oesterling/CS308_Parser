package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Home extends TurtleCommand {

  /**
   * Sends the turtle back to the home positions
   */
  public Home() {
    super.goHome();
  }
}
