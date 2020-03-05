package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class PenUp extends Command {

    private Turtle t;
    private static final double NOT_DRAWING = 0;

    /**
     * Constructor for pen up which allows the turtle to move without drawing, lifts pen
     * sets the drawing to false
     * @param body the current turtle that will have its pen set up and not drawing
     */
    public PenUp(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
        super(NOT_DRAWING);
        t = body;
    }

    /**
     * Allows the command pen up to be executed
     * @return 0 for false (not drawing)
     */
    @Override
    public Double execute() {
        t.setDrawing(NOT_DRAWING);
        return NOT_DRAWING;
    }
}