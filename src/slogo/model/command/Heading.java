package slogo.model.command;

import slogo.model.Turtle;

public class Heading extends Command {

    private Turtle t;
    /**
     * Heading constructor, gets the turtles current heading in degrees
     * @param body the current turtle being used
     */
    public Heading(Turtle body){
        super();
        t = body;
    }

    /**
     * Allows the heading command to be execute and return the turtle's heading in degrees
     * @return turtle heading in degrees
     */
    @Override
    public Double execute() {
        return t.getHeading();
    }
}
