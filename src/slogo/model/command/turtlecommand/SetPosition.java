package slogo.model.command.turtlecommand;

import slogo.model.Turtle;

public class SetPosition extends TurtleCommand {

    /**
     * Set position constructor, sets position of turtle without actually turning it, moves to that position
     * Calls super and sets the new position, with turtle facing same way
     * @param body the current turtle that is going to have its position changed
     * @param xPos the new x position that the turtle will be at
     * @param yPos the new  y position that the turtle will be at
     */
    public SetPosition(Turtle body, double xPos, double yPos){
        super(body.setPosition(xPos, yPos));
    }
}
