package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Right extends TurtleCommand {

    public Right(Turtle body, double value) {
        super(value);
        body.turn(value);
    }
}
