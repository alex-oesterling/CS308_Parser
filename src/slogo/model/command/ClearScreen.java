package slogo.model.command;

import slogo.model.Turtle;

public class ClearScreen extends Command {
    /**
     * The  clear screen constructor which erases the turtle's trails and sends it to its home position
     * @param body the current turtle being used
     */
    public ClearScreen(Turtle body){
        super(body.goHome());
    }
}
