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
     * Allows the clear screen command to be executed, clears the
     * screen and returns the distance the turtle moved
     * @return distance the turtle moved
     */
    @Override
    public Double execute() {
        return t.goHome();
    }
}
