package slogo.model.command.turtlecommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

public class PenDown extends Command {

    private static final double DRAWING = 1;
    /**
     * Constructor for pen down which allows the turtle to  start drawing/use its pen to draw
     * sets the drawing to true
     * @param body the current turtle that will have its pen set to down
     */
    public PenDown(Turtle body){
        super();
        body.setDrawing(DRAWING);
    }
}
