package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class Backward extends TurtleCommand {
    public Backward(Turtle body, double value) {
        super(body, value);
        goBackwards(body, value);
    }

    private void goBackwards(Turtle body, double value){

    }
}
