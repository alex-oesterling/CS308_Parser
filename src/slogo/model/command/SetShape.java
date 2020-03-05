package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class SetShape extends Command {

    private Double shape;
    private Turtle t;

    /**
     * Set shape constructor, takes in the index for the shape
     * of which the current turtle should be changed to
     * @param index the shape to be set to
     * @param body the turtle being used
     */
    public SetShape(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
        super();
        shape = index;
        t = body;
    }

    /**
     * Gets the result/the index for the shape to be set to
     * @return the shape index
     */
    @Override
    public Double getResult() {
        return t.getShape();
    }

    /**
     * Allows the set shape command to be executed
     * @return calls the get result to return index of specific shape
     */
    @Override
    public Double execute() {
        t.setShape(shape);
        return this.getResult();
    }
}
