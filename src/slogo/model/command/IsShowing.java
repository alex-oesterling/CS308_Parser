package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class IsShowing extends Command {

    private Turtle t;

    /**
     * Return 1 if the turtle is showing, 0 otherwise
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     */
    public IsShowing(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList){
        super(turtleList.get(FIRST_INDEX).isTurtleVisible());
        t = turtleList.get(FIRST_INDEX);
    }

    /**
     * Return if the pen is down on a turtle or not
     * this is separate from setting it in the constructor
     * in case a specific IsPenDown object's execute() is
     * called many times; i.e. object is in a loop
     * @return drawing status of the turtle
     */
    @Override
    public Double getResult(){
        return t.isTurtleVisible();
    }
}
