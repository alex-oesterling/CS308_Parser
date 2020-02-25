package slogo.model.command;

import slogo.model.Turtle;

public class YCoordinate extends Command {

    private Turtle t;
    /**
     * Takes in a turtle, and returns the y coordinate of that turtle
     * @param body turtle
     */
    public YCoordinate(Turtle body){
        super();
        t = body;
    }

    /**
     * Allows the yCoordinate command to be executed
     * @return the y coordinate of the turtle
     */
    @Override
    public Double execute() {
        return t.getY();
    }
}
