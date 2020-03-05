package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class SetPenColor extends Command{

    private Double color;
    private Turtle t;

    /**
     * Set pen color constructor, takes in the index of the color
     * for which the pen should be set to
     * @param index the color to be set to
     * @param body the turtle being used
     */
    public SetPenColor(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
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
        return t.getPenColor();
    }

    /**
     * Allows the set pen color command to be executed
     * @return calls the get result to return index
     */
    @Override
    public Double execute() {
        t.setPenColor(color);
        return this.getResult();
    }
}
