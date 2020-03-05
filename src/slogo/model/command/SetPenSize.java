package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class SetPenSize extends Command {

    private Double size;
    private Turtle t;

    /**
     * Set pen size constructor, takes in the number of pixels for the
     * size of the pen to be switched to
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     */
    public SetPenSize(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
        super();
        size = doubleList.get(FIRST_INDEX);
        t = turtleList.get(FIRST_INDEX);
    }

    /**
     * Gets the result/the number of pixels for the shape to be set to
     * @return the size to be set to
     */
    @Override
    public Double getResult() {
        return size;
    }

    /**
     * Allows this command and the setting of pen size to be executed
     * @return calls the get result to return pixels/size
     */
    @Override
    public Double execute() {
        t.setPenSize(size);
        return this.getResult();
    }
}
