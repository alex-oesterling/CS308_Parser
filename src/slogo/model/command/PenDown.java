package slogo.model.command;

import slogo.model.Turtle;

public class PenDown extends Command {

    private Turtle t;
    private static final double DRAWING = 1;
    /**
     * Constructor for pen down which allows the turtle to  start drawing/use its pen to draw
     * sets the drawing to true
     * @param body the current turtle that will have its pen set to down
     */
    public PenDown(Turtle body){
        super();
        t = body;
    }

    /**
     * Allows the penDown command to be executed
     * @return 1 as true, meaning the pen is down
     */
    @Override
    public Double execute() {
        t.setDrawing(DRAWING);
        return DRAWING;
    }
}