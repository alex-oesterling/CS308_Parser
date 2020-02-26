package slogo.model.command;

import slogo.model.Turtle;

public class Left extends Command {

    private static final Double LEFT = -1.0;
    private Turtle t;
    private Double degrees;

    /**
     * Left constructor, to get the value to be a left value
     * Calls super and sets the left turn value
     * @param body the specific turtle being used, what the left is being set for
     * @param value the value of how far it is being turned to the left (changed to negative)
     */
    public Left(Turtle body, Double value) {
        super(value);
        t = body;
        degrees = value;
    }

    /**
     * Allows the left command to be executed
     * @return the value turned in degrees
     */
    @Override
    public Double execute() {
        t.turn(degrees*LEFT);
        return this.getResult();
    }

}
