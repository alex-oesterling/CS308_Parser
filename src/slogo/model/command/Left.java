package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Left extends Command {

    private static final Double LEFT = -1.0;
    private Turtle t;
    private Double degrees;

    /**
     * Left constructor, to get the value to be a left value
     * Calls super and sets the left turn value
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     */
    public Left(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList) {
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
