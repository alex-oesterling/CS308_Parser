package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class PenUp extends TurtleCommand {

    /**
     * Constructor for pen up which allows the turtle to move without drawing, lifts pen
     * sets the drawing to false
     * @param body the current turtle that will have its pen set up and not drawing
     */
    public PenUp(Turtle body){
        super();
        body.setDrawing(false);
    }
}
