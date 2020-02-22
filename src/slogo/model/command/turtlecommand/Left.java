package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Left extends TurtleCommand {

    private double left;

    /**
     * Left constructor, to get the value to be a left value
     * Calls super and sets the left turn value
     * @param body the specific turtle being used, what the left is being set for
     * @param value the value. the value of how far it is being turned to the left (changed to negative)
     */
    public Left(Turtle body, double value) {
        super(value);
        left = value * -1;
        body.turn(left);
    }
}
