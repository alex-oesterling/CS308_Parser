package slogo.model.command;

import slogo.model.Turtle;

public class ID extends Command {

    private Turtle t;

    /**
     * Constructor for the ID command
     * @param body the turtle being used
     */
    public ID(Turtle body){
        super();
        t = body;
    }

    /**
     * Gets the current id of the active turtle
     * @return the id of the current turtle
     */
    @Override
    public Double getResult() {
        return t.getId();
    }
}
