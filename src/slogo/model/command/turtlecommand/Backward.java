package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Backward extends TurtleCommand {

    private double back;
    public Backward(Turtle body, double value) {
        super(value);
        back = value *-1;
        body.move(back);
    }
}
