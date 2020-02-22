package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Backward extends TurtleCommand {

    private double back;

    /**
     * Backward constructor, to get the value of going backward
     * Calls super and sets the backward value
     * @param body the specific turtle being used, what the backward value is being set for
     * @param value the value. the value of how far it is going back (changed to negative)
     */
    public Backward(Turtle body, double value) {
        super(value);
        back = value *-1;
        body.move(back);
    }
}
