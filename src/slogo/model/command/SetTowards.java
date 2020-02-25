package slogo.model.command;

import slogo.model.Turtle;

public class SetTowards extends Command {
    /**
     * Set Towards constructor, turns the turtle to face the given point
     * @param body the turtle currently being used
     * @param xPos  the x position of where the turtle will turn to
     * @param yPos the y position of where the turtle will turn to
     */
    public SetTowards(Turtle body, Double xPos, Double yPos){
        super(body.pointTowards(xPos, yPos));
    }
}
