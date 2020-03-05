package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class SetBackground extends Command {

    private Double color;
    private Turtle t;

    /**
     * Set background constructor, takes in the index of the color
     * for which the background should be set to
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     */
    public SetBackground(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
        super();
        color = doubleList.get(FIRST_INDEX);
        t = turtleList.get(FIRST_INDEX);
    }

    /**
     * Gets the result/the index for the color to be set to
     * @return the color index
     */
    @Override
    public Double getResult() {
        return color;
    }

    /**
     * Allows this command and the setting of background color to be executed
     * @return calls the get result to return color/index
     */
    @Override
    public Double execute() {
        t.setBackground(color);
        return this.getResult();
    }
}
