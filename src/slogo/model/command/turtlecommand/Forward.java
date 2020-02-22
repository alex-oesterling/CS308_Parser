package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Forward extends TurtleCommand {

  /**
   * Forward constructor, to get the value for going forward
   * Calls super and sets the forward value
   * @param body the specific turtle being used, what the forward value will be
   * @param value the value. the value of how far it is going forward
   */
  public Forward(Turtle body, double pos) {
    super(pos);
    body.move(pos);
  }
}
