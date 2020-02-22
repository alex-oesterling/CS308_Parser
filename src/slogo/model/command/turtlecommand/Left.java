package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Left extends TurtleCommand {

    private double left;
    public Left(Turtle body, double value) {
        super(value);
        left = value * -1;
        body.turn(left);
    }
}
