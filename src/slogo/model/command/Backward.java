package slogo.model.command;

import slogo.model.Turtle;

public class Backward extends Command {

    private static final Double BACK = -1.0;

    /**
     * Backward constructor, to get the value of going backward
     * Calls super and sets the backward value
     * @param body the specific turtle being used, what the backward value is being set for
     * @param value the value. the value of how far it is going back (changed to negative)
     */
    public Backward(Turtle body, Double value) {
        super(value);
        body.move(value*BACK);
    }
}
