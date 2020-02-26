package slogo.model.command;

import slogo.model.Turtle;

public class ClearScreen extends Command {

    private Turtle t;

    /**
     * The  clear screen constructor which erases the turtle's trails and sends it to its home position
     * @param body the current turtle being used
     */
    public ClearScreen(Turtle body){
        super();
        t = body;
    }

    /**
     * Override super getResult();
     * this is done here instead of setting in constructor
     * in case the turtle moves and the same command object is executed again
     * @return distance to Home, the distance the turtle will travel when executed
     */
    @Override
    public Double getResult(){
        return t.distanceToPosition(Turtle.DEFAULT_STARTING_X, Turtle.DEFAULT_STARTING_Y);
    }

    /**
     * Allows the clear screen command to be executed, clears the
     * screen and returns the distance the turtle moved
     * @return distance the turtle moved
     */
    @Override
    public Double execute() {
        t.goHome();
        return this.getResult();
    }
}
