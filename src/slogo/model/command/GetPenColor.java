package slogo.model.command;

import slogo.model.Turtle;

public class GetPenColor extends Command {

    private Turtle t;

    /**
     * Get pen color constructor, returns the current index of the color of the pen
     * @param body the current turtle being used
     */
    public GetPenColor(Turtle body){
        super();
        t = body;
    }

    /**
     * Return the current pen color of the turtle
     * @return the turtle's pen color/index
     */
    @Override
    public Double getResult(){
        return t.getPenColor();
    }

    /**
     * Allows the get pen color command to be executed
     * @return call for the get result to return index/color
     */
    @Override
    public Double execute() {
        return this.getResult();
    }
}
