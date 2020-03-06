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
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     * @param stringList the list of strings being used for this command (if needed)
     */
    public SetTowards(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
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