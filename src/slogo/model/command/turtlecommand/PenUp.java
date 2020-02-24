package slogo.model.command.turtlecommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

public class PenUp extends Command {

    private static final double NOT_DRAWING = 0;
    /**
     * Constructor for pen up which allows the turtle to move without drawing, lifts pen
     * sets the drawing to false
     * @param body the current turtle that will have its pen set up and not drawing
     */
    public PenUp(Turtle body){
        super(body.setDrawing(NOT_DRAWING));
    }
}
