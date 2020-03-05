package slogo.model.command;

import slogo.model.Turtle;

public class Turtles extends Command {

    private Turtle t;

    /**
     * Constructor for turtles commands, returns the current number of turtles created
     * @param body turtle being used
     */
    public Turtles(Turtle body){
        super();
        t = body;
    }

    /**
     * Gets the amount of turtles created
     * @return the amount of turtles created
     */
    @Override
    public Double getResult() {
        return t.getTurtleCount();
    }
}