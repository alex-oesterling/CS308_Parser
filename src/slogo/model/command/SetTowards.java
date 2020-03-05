package slogo.model.command;

import java.util.List;
import java.util.Set;
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
    public SetTowards(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
        super();
        t = turtleList.get(FIRST_INDEX);
        xPos = doubleList.get(SECOND_INDEX);
        yPos = doubleList.get(FIRST_INDEX);
    }
    @Override
    public Double getResult(){
        return t.headingTowards(xPos, yPos);
    }

    /**
     * Point the turtle towards xPos, yPos
     * @return angle changed
     */
    @Override
    public Double execute() {
        t.pointTowards(xPos,yPos);
        return this.getResult();
    }

}