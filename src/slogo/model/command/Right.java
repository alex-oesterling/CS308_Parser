package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Right extends Command {

    private Turtle t;
    private Double degrees;
    /**
     * Right constructor, to get the value of going right
     * Calls super and sets the right turn value
     * @param body the specific turtle being used, what the right value will be
     * @param value the value of how far it is turning right
     */
    public Right(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList) {
        super(value);
        t = body;
        degrees = value;
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
