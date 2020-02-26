package slogo.model.command;

import slogo.model.Turtle;

public class IsPenDown extends Command {

    private Turtle t;
    /**
     * Return 1 if the pen is down, 0 otherwise
     * @param body turtle with the pen
     */
    public IsPenDown(Turtle body){
        super();
        t = body;
    }

    /**
     * Return if the pen is down on a turtle or not
     * this is separate from setting it in the constructor
     * in case a specific IsPenDown object's execute() is
     * called many times; i.e. object is in a loop
     * @return drawing status of the turtle; 1 if drawing or 0 if not drawing
     */
    @Override
    public Double getResult(){
        return t.getDrawingStatus();
    }
}
