package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class ShowTurtle {

    /**
     * Constructor for show turtle which allows the turtle to be seen by setting visibility to be true
     * @param body the turtle that is currently being used, will set to be shown
     */
    public ShowTurtle(Turtle body){
        super();
        body.setVisibility(true);
    }
}
