package slogo.model.command;

import slogo.model.Turtle;

public class IsShowing extends Command {

    private Turtle t;

    /**
     * Return 1 if the turtle is showing, 0 otherwise
     * @param body turtle to check
     */
    public IsShowing(Turtle body){
        super();
        t = body;
    }

    /**
     * Allows the isShowing command to be executed to tell whether the turtle is showing or not
     * @return 0 if not showing, 1 if showing
     */
    @Override
    public Double execute() {
       return t.isTurtleVisible();
    }
}
