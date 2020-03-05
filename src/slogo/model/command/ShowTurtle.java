package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class ShowTurtle extends Command {

    private static final double SHOWING = 1;
    private Turtle t;

    /**
     * Constructor for show turtle which allows the turtle to be seen
     * @param body the turtle that is currently being used, will set to be shown
     */
    public ShowTurtle(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
        super(SHOWING);
        t = body;
    }

    /**
     *  Setting turtle visibility to be true, and return true (1) for showing
     * @return 1
     */
    @Override
    public Double execute() {
        t.setVisibility(SHOWING);
        return SHOWING;
    }
}