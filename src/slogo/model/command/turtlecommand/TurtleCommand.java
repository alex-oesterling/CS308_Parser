package slogo.model.command.turtlecommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

abstract public class TurtleCommand extends Command {

  private Turtle myTurtle;
  private double result;

  /**
   * TurtleCommand constructor with an incoming Turtle
   * @param body turtle the changes apply to
   * @param value result to be returned
   */
  public TurtleCommand(Turtle body, double value){
    super();
    //TODO check that body != null
    myTurtle = body;
    result = value;
  }

  /**
   * change the xPosition of the current turtle
   * @param newXPosition value to give to the turtle's setter
   */
  protected void updateX(double newXPosition){
    myTurtle.setX(newXPosition);
  }

  /**
   * change the yPosition of the current turtle
   * @param newYPosition value to give to the turtle's setter
   */
  protected void updateY(double newYPosition){
    myTurtle.setY(newYPosition);
  }

  /**
   * call goHome on my current turtle
   */
  protected void sendHome(){
    myTurtle.goHome();
  }

  @Override
  public double getResult(){
    return result;
  }
}
