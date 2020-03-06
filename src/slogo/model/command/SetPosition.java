package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class SetPosition extends Command {

    private Turtle t;
    private Double xPos;
    private Double yPos;

    /**
     * Set position constructor, sets position of turtle without actually turning it, moves to that position
     * Calls super and sets the new position, with turtle facing same way
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     * @param stringList the list of strings being used for this command (if needed)
     */
    public SetPosition(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
        super();
        t = turtleList.get(FIRST_INDEX);
        xPos = doubleList.get(FIRST_INDEX);
        yPos = doubleList.get(SECOND_INDEX);
    }

    /**
     * Move to position xPos, yPos, and return the distance travelled
     * @return distance travelled by the turtle
     */
    @Override
    public Double execute(){
        return t.moveToPosition(xPos, yPos);
    }
    public Double getResult() {
        return t.distanceToPosition(xPos, yPos);
    }

}