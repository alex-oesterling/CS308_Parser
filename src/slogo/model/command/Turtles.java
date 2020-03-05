package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class Turtles extends Command {

    private Turtle t;

    /**
     * Constructor for turtles commands, returns the current number of turtles created
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     */
    public Turtles(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
        super();
        t = turtleList.get(FIRST_INDEX);
    }

    /**
     * Gets the amount of turtles created
     * @return the amount of turtles created
     */
    @Override
    public Double getResult() {
        return t.getTurtleCount();
    }
}