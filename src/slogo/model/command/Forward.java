package slogo.model.command;

import slogo.controller.Controller;
import slogo.model.Turtle;
import slogo.view.Visualizer;

import java.sql.SQLOutput;

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
    super(value);
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

  /**
   * Get the distance to be returned
   * @return distance
   */
  @Override
  public Double getResult() {
    return distance;
  }

}

