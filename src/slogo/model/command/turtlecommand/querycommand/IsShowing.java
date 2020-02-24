package slogo.model.command.turtlecommand.querycommand;

import slogo.model.Turtle;

public class IsShowing extends QueryCommand {

    /**
     * Return 1 if the turtle is showing, 0 otherwise
     * @param body turtle to check
     */
    public IsShowing(Turtle body){
        super(body.isTurtleVisible());
    }
}
