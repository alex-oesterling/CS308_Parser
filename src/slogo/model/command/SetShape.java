package slogo.model.command;

public class SetShape extends Command {

    private Double shape;

    /**
     * Set shape constructor, takes in the index for the shape
     * of which the current turtle should be changed to
     * @param index the shape to be set to
     */
    public SetShape(Double index){
        super();
        shape = index;
    }

    /**
     * Gets the result/the index for the shape to be set to
     * @return the shape index
     */
    @Override
    public Double getResult() {
        return shape;
    }
}
