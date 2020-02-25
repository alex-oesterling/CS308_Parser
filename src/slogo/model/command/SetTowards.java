package slogo.model.command.turtlecommand;

import slogo.model.Turtle;
import slogo.model.command.Command;


public class SetTowards extends Command {
    /**
     * Set Towards constructor, turns the turtle to face the given point
     * @param body the turtle currently being used
     * @param xPos  the x position of where the turtle will turn to
     * @param yPos the y position of where the turtle will turn to
     */
    public SetTowards(Turtle body, double xPos, double yPos){
        super(body.pointTowards(xPos, yPos));
    }
}
