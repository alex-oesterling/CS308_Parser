package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class SetBackground extends Command {

    private Double color;
    private Turtle t;

    /**
     * Set background constructor, takes in the index of the color
     * for which the background should be set to
     * @param index the color to be set to
     * @param body the turtle being used
     */
    public SetBackground(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
        super();
        color = index;
        t = body;
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
