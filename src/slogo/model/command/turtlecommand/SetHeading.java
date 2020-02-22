package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class SetHeading extends TurtleCommand {

    /**
     * Set Heading constructor, takes in the value and turns the turtle to that
     * specific value
     * @param body the turtle being used and what will turn to value
     * @param value the value/angle that the value will turn to
     */
    public SetHeading(Turtle body, double value){
        super(value);
        body.setHeading(value);
    }
}
