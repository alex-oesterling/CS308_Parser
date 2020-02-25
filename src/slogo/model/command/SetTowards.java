package slogo.model.command;

import slogo.model.Turtle;

public class SetTowards extends Command {

    private Turtle t;
    private Double xPos;
    private Double yPos;

    /**
     * Set Towards constructor, turns the turtle to face the given point
     * @param body the turtle currently being used
     * @param x the x position of where the turtle will turn to
     * @param y the y position of where the turtle will turn to
     */
    public SetTowards(Turtle body, Double x, Double y){
        super();
        t = body;
        xPos = x;
        yPos = y;
    }

    /**
     * Point the turtle towards xPos, yPos
     * @return angle changed
     */
    @Override
    public Double execute() {
        return t.pointTowards(xPos, yPos);
    }
}