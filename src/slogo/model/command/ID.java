package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class ID extends Command {
    private Turtle t;

    /**
     * Constructor for the ID command
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     */
    public ID(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
        super();
        t = turtleList.get(FIRST_INDEX);
    }

    /**
     * Gets the current id of the active turtle
     * @return the id of the current turtle
     */
    @Override
    public Double getResult() {
        return t.getId();
    }
}
