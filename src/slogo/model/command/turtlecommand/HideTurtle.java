package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class HideTurtle {

    /**
     * Constructor for hide turtle which allows the turtle to be hidden by setting visibility to be false
     * @param body the turtle that is currently being used, will set to be hidden
     */
    public HideTurtle(Turtle body){
        super();
        body.setVisibility(false);
    }
}
