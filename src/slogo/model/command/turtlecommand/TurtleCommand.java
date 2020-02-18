package slogo.model.command.turtlecommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

abstract public class TurtleCommand extends Command {

  Turtle myTurtle;

  /*
  public TurtleCommand(){
    super();
  }

  public TurtleCommand(double xPos, double yPos, int heading){
    this();
    //TODO check that xPos and yPos are valid, if not, don't execute following line
    myTurtle = new Turtle(xPos, yPos, heading);
  }*/

  /**
   * TurtleCommand constructor with an incoming Turtle
   * @param body
   */
  public TurtleCommand(Turtle body){
    super();
    //TODO check that body != null
    myTurtle = body;
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

  /*
  /**
   * getter for the turtle effected by these commands
   * @return myTurtle

  public Turtle getTurtle(){
    return myTurtle;
  }
   */

}
