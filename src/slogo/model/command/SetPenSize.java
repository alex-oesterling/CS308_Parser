package slogo.model.command;

import slogo.model.Turtle;

public class SetPenSize extends Command {

    private Double size;
    private Turtle t;

    /**
     * Set pen size constructor, takes in the number of pixels for the
     * size of the pen to be switched to
     * @param pixels the size for pen to be set to
     * @param body the turtle being used
     */
    public SetPenSize(Turtle body, Double pixels){
        super();
        size = pixels;
        t = body;
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
