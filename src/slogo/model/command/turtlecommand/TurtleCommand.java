package slogo.model.command.turtlecommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

abstract public class TurtleCommand extends Command {

  private static final double NOTHING = 0;
  private Turtle myTurtle;
  private double result;

  /**
   * TurtleCommand constructor with an incoming Turtle,
   * and a value to set result to
   * @param value result to be returned
   */
  public TurtleCommand(double value){
    super();
    //TODO check that body != null
    result = value;
  }

  /**
   * TurtleCommand constructor with an incoming Turtle
   * with no value to set result to
   */
  public TurtleCommand(){
    this(NOTHING);
  }

  @Override
  public double getResult(){
    return result;
  }
}
