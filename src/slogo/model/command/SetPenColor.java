package slogo.model.command;

public class SetPenColor extends Command{

    private Double color;

    /**
     * Set pen color constructor, takes in the index of the color
     * for which the pen should be set to
     * @param index the color to be set to
     */
    public SetPenColor(Double index){
        super();
        color = index;
    }

    /**
     * Gets the result/the index for the color to be set to
     * @return the color index
     */
    @Override
    public Double getResult() {
        return color;
    }
}
