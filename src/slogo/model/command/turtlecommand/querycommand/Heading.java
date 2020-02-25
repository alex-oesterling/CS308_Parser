package slogo.model.command.turtlecommand.querycommand;

import slogo.model.Turtle;
import slogo.model.command.Command;

public class Heading extends Command {

    /**
     * Heading constructor, gets the turtles current heading in degrees
     * @param body the current turtle being used
     */
    public Heading(Turtle body){ super(body.getHeading()); }
}
