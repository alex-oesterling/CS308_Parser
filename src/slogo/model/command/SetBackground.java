package slogo.model.command;

public class SetBackground extends Command {

    private Double color;

    /**
     * Set background constructor, takes in the index of the color
     * for which the background should be set to
     * @param index the color to be set to
     */
    public SetBackground(Double index){
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
