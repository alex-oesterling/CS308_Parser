package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class GetPenColor extends Command {

    private Turtle t;

    /**
     * Get pen color constructor, returns the current index of the color of the pen
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     * @param stringList the list of strings being used for this command (if needed)
     */
    public GetPenColor(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
        super();
        t = turtleList.get(FIRST_INDEX);
    }

    /**
     * Return the current pen color of the turtle
     * @return the turtle's pen color/index
     */
    @Override
    public Double getResult(){
        return t.getPenColor();
    }

    /**
     * Allows the get pen color command to be executed
     * @return call for the get result to return index/color
     */
    @Override
    public Double execute() {
        return this.getResult();
    }
}
