package slogo.model.command;

import slogo.model.Turtle;

public class Heading extends Command {

    /**
     * Heading constructor, gets the turtles current heading in degrees
     * @param body the current turtle being used
     */
    public Heading(Turtle body){ super(body.getHeading()); }
}
