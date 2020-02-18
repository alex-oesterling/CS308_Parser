package slogo.model.command;

import slogo.model.Turtle;

public class TurtleCommand extends Command{

  Turtle myTurtle;

  /**
   * default constructor that creates a new turtle
   */
  public TurtleCommand(){
    super();
    myTurtle = new Turtle();
  }

  public TurtleCommand(double xPos, double yPos){
    this();
    //TODO check that xPos and yPos are valid, if not, don't execute following line
    myTurtle = new Turtle(xPos, yPos);
  }

  /**
   * TurtleCommand constructor with an incoming Turtle
   * @param body
   */
  public TurtleCommand(Turtle body){
    this();
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

}
