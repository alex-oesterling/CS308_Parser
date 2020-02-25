package slogo.model.command;

import slogo.model.Turtle;

public class IsShowing extends Command {

    /**
     * Return 1 if the turtle is showing, 0 otherwise
     * @param body turtle to check
     */
    public IsShowing(Turtle body){
        super(body.isTurtleVisible());
    }
}
