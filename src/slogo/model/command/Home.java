package slogo.model.command.turtlecommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

public class Home extends Command {

  /**
   * Sends the turtle back to the home positions
   */
  public Home(Turtle body) {
    super(body.goHome());
    System.out.println("home");
  }
}
