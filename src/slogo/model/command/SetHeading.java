package slogo.model.command;

import slogo.model.Turtle;

import java.util.List;

public class SetHeading extends Command {

    private Turtle t;
    private Double heading;

    /**
     * Set Heading constructor, takes in the value and turns the turtle to that
     * specific value
     * @param turtleList the list of turtles being brought in to use this command (if needed)
     * @param doubleList the list of doubles to be used for this command (if needed)
     * @param commandList the list of commands being used for this command (if needed)
     * @param stringList the list of strings being used for this command (if needed)
     */
    public SetHeading(List<Turtle> turtleList, List<Double> doubleList, List<List<Command>> commandList, List<String> stringList){
        super();
        t = turtleList.get(FIRST_INDEX);
        heading = doubleList.get(FIRST_INDEX);
    }

    /**
     * Gets the result of setting the heading
     * @return number of degrees moved
     */
    @Override
    public Double getResult(){
        return t.getDeltaTheta(heading, t.getHeading());
    }

    /**
     * Allows the SetHeading command to be executed
     * @return the value of the heading, where the turtle is facing
     */
    @Override
    public Double execute() {
        t.setHeading(heading);
        return this.getResult();
    }
}