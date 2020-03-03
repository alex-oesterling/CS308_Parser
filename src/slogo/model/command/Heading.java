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
     * Return the current heading of the turtle
     * this is separate from setting it in super in
     * case this command is called within a loop
     * @return the turtle's heading
     */
    @Override
    public Double getResult(){
        return t.getHeading();
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
