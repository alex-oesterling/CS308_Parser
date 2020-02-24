package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Right extends TurtleCommand {

    /**
     * Right constructor, to get the value of going right
     * Calls super and sets the right turn value
     * @param body the specific turtle being used, what the right value will be
     * @param value the value of how far it is turning right
     */
    public Right(Turtle body, double value) {
        super(value);
        body.turn(value);
    }
}