package slogo.model.command;

import slogo.model.Turtle;

public class Home extends Command {

  private Turtle t;

  /**
   * Constructor for the home command
   * @param body turtle being used currently
   */
  public Home(Turtle body) {
    super();
    t = body;
  }

  /**
   * Allows home command to be executed and sends turtle back to starting position
   * @return the distance the turtle moved
   */
  @Override
  public Double execute() {
    return t.goHome();
  }
}
