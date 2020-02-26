package slogo.model.command;

import slogo.model.Turtle;

public class Forward extends Command {

  private Turtle t;
  private Double distance;

  /**
   * Forward constructor, to get the value for going forward
   * Calls super
   * @param body the specific turtle being used, what the forward value will be
   * @param value the value of how far it is going forward
   */
  public Forward(Turtle body, Double value) {
    super();
    t = body;
    distance = value;
  }

  /**
   * Allows the turtle to move forward
   * @return distance travelled
   */
  @Override
  public Double execute(){
    t.move(distance);
    return distance;
  }

}

