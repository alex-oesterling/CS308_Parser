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
   * Override super getResult();
   * this is done here instead of setting in constructor
   * in case the turtle moves and the same command object is executed again
   * @return distance to Home
   */
  @Override
  public Double getResult(){
    return t.distanceToPosition(Turtle.DEFAULT_STARTING_X, Turtle.DEFAULT_STARTING_Y);
  }

  /**
   * Allows home command to be executed and sends turtle back to starting position
   * @return the distance the turtle moved
   */
  @Override
  public Double execute() {
    t.goHome();
    return t.distanceToPosition(Turtle.DEFAULT_STARTING_X, Turtle.DEFAULT_STARTING_Y);
  }
}
