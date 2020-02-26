package slogo.model.command;

import slogo.model.Turtle;

public class IsShowing extends Command {

    private Turtle t;

    /**
     * Return 1 if the turtle is showing, 0 otherwise
     * @param body turtle to check
     */
    public IsShowing(Turtle body){
        super(body.isTurtleVisible());
        t = body;
    }

    /**
     * Return if the pen is down on a turtle or not
     * this is separate from setting it in the constructor
     * in case a specific IsPenDown object's execute() is
     * called many times; i.e. object is in a loop
     * @return drawing status of the turtle
     */
    @Override
    public Double getResult(){
        return t.isTurtleVisible();
    }
}
