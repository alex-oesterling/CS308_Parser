package slogo.model.command;

import slogo.model.Turtle;

public class Home extends Command {

  /**
   * Sends the turtle back to the home positions
   */
  public Home(Turtle body) {
    super(body.goHome());
  }
}
