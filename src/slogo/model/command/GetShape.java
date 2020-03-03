package slogo.model.command;

import slogo.model.Turtle;

public class GetShape extends Command {

    private Turtle t;

    /**
     * Get shape constructor, returns the current index of the shape of the turtle
     * @param body the current turtle being used
     */
    public GetShape(Turtle body){
        super();
        t = body;
    }

    /**
     * Return the current shape of the turtle
     * @return the turtle's shape/index
     */
    @Override
    public Double getResult(){
        return t.getShape();
    }

    /**
     * Allows the get shape command to be executed
     * @return call for the get result to return index/shape
     */
    @Override
    public Double execute() {
        return this.getResult();
    }
}
