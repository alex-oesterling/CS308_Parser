package slogo.model.command;

import slogo.model.Turtle;

public class ShowTurtle extends Command {

    private static final double SHOWING = 1;
    /**
     * Constructor for show turtle which allows the turtle to be seen by setting visibility to be true
     * @param body the turtle that is currently being used, will set to be shown
     */
    public ShowTurtle(Turtle body){
        super(SHOWING);
        body.setVisibility(SHOWING);
    }
}
