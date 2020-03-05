package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Right extends Command {

    private Turtle t;
    private Double degrees;
    /**
     * Right constructor, to get the value of going right
     * Calls super and sets the right turn value
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     */
    public Right(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList) {
        super(doubleList.get(FIRST_INDEX));
        t = turtleList.get(FIRST_INDEX);
        degrees = doubleList.get(FIRST_INDEX);
    }

    /**
     * Allows the right command to be executed
     * @return the degrees turned
     */
    @Override
    public Double execute() {
        return t.turn(degrees);
    }

}
